package com.benckw69.learningPlatform_java.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.benckw69.learningPlatform_java.AdminConfig.EventCategory;
import com.benckw69.learningPlatform_java.AdminConfig.MoneyRecord;
import com.benckw69.learningPlatform_java.AdminConfig.MoneyRecordService;
import com.benckw69.learningPlatform_java.AdminConfig.Referral;
import com.benckw69.learningPlatform_java.AdminConfig.ReferralService;

import jakarta.servlet.http.HttpSession;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IntroductionService introductionService;

    @Autowired
    private ReferralService referralService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MoneyRecordService moneyRecordService;

    public Boolean isSamePassword(String password, String password_repeat, Model model){
        if(!password.equals(password_repeat)) {
            model.addAttribute("passwordRepeatError", "確認密碼與密碼不一樣");
            return false;
        }
        return true;
    }

    public Boolean emailExist(String email,Model model){
        User existEmail = userRepository.findByEmailIgnoreCaseAndLoginMethod(email,LoginMethod.email);
        if(existEmail != null) {
            model.addAttribute("emailError", "此電郵地址已被註冊");
            return true;
        }
        return false;
    }

    public Boolean passwordMatch(String matchingPassword, String password, Model model){
        if(!passwordEncoder.matches(matchingPassword, password)) {
            model.addAttribute("passwordError", "密碼錯誤");
            return false;
        }
        return true;
    }

    public Boolean usernameBlank(String username, Model model){
        if(username.trim().equals("")) {
            model.addAttribute("usernameError", "用戶名稱不能為空");
            return true;
        }
        return false;
    }

    

    public User updateUser(User user){
        return userRepository.save(user);
    }

    public User getUserBySession(HttpSession httpSession){
        return (User)httpSession.getAttribute("user");
    }

    public User getAdmin(){
        return userRepository.findByType(Type.admin);
    }

    

    public List<User> getUsersByUsernameOrderByDate(String username){
        return userRepository.findByUsernameContainsIgnoreCaseAndIsDeleted(username, false);
    }

    public List<User> getDeletedUsersOrderByDate(){
        return userRepository.findByIsDeletedOrderByUpdatedTimeDesc(true);
    }

    public List<User> getDeletedUsersByIdOrderByDate(Integer id){
        return userRepository.findByIdAndIsDeletedOrderByUpdatedTimeDesc(id, true);
    }

    public List<User> getDeletedUsersByUsernameOrderByDate(String username){
        return userRepository.findByUsernameContainsIgnoreCaseAndIsDeletedOrderByUpdatedTimeDesc(username, true);
    }

    public List<User> getDeletedUsersByEmailOrderByDate(String email){
        return userRepository.findByEmailContainsIgnoreCaseAndIsDeletedOrderByUpdatedTimeDesc(email, true);
    }

    public void resetDeletedUser(Integer id){
        User user = userRepository.findById(id).orElse(null);
        user.setIsDeleted(false);
        userRepository.save(user);
    }

    public Boolean validRegister(RegisterRequest registerRequest,Model model){
        //Validation: confirm password and email existance
        Boolean validation1 = !emailExist(registerRequest.getEmail(), model);
        Boolean validation2 = isSamePassword(registerRequest.getPassword(), registerRequest.getPassword_repeat(), model);
        Boolean validation3 = !usernameBlank(registerRequest.getUsername(), model);
        return validation1 && validation2 && validation3;
    }
        

    public void register(RegisterRequest registerRequest){
        //Set new user
        User newUser = new User();
        newUser.setType(registerRequest.getType());
        newUser.setEmail(registerRequest.getEmail().trim());
        newUser.setUsername(registerRequest.getUsername().trim());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setLoginMethod(LoginMethod.email);

        //referral system. Get the settings at database first, then apply the settings
        MoneyRecord moneyRecord = new MoneyRecord();
        MoneyRecord moneyRecord2 = new MoneyRecord();
        Boolean moneyRecord1Set = false;
        Boolean moneyRecord2Set = false;
        Referral referralSetting = referralService.getReferralConfig();
        User referral = new User();

        if(registerRequest.getReferral()!=null && registerRequest.getReferral()!=""){
            Integer referralId = Integer.parseInt(registerRequest.getReferral());
            referral = userRepository.findByIdAndIsDeleted(referralId,false).orElse(null);
            if(referral != null) {
                Integer newUserAmount = referralSetting.getNewUserAmount();
                Integer referralAmount = referralSetting.getNewUserAmount();
                if(referralSetting.getReferralGet()){
                    //Set money record
                    moneyRecord.setEventConsequence(1);
                    moneyRecord.setEventCategory(EventCategory.REFERRAL_BONUS);
                    moneyRecord.setMoneyChange(referralSetting.getReferralAmount());
                    moneyRecord.setUser(referral);
                    userRepository.updateBalance(referralAmount, referralId);
                    moneyRecord1Set = true;
                }
                if(referralSetting.getNewUserGet()){
                    //Set money record
                    Integer consequence = referralSetting.getReferralGet()? 2:1;
                    moneyRecord2.setEventConsequence(consequence);
                    moneyRecord2.setEventCategory(EventCategory.REFERRAL_BONUS);
                    moneyRecord2.setMoneyChange(referralSetting.getNewUserAmount());
                    newUser.setBalance(newUserAmount);
                    moneyRecord2Set = true;
                }
            }
        }

        //save new user
        newUser = userRepository.save(newUser);

        //save introduction
        if(registerRequest.getType() == Type.teacher){
            Introduction newIntroduction = new Introduction();
            newIntroduction.setUser(newUser);
            newIntroduction.setDefaultIntroduction(newUser);
            introductionService.updateIntroduction(newIntroduction);
        }

        //save money record(s)
        if(moneyRecord1Set){
            moneyRecord.setEventText(EventCategory.REFERRAL_BONUS, referral, referralSetting, newUser);
            moneyRecordService.updateMoneyRecord(moneyRecord);
        }
        if(moneyRecord2Set){
            moneyRecord2.setUser(newUser);
            moneyRecord2.setEventText(EventCategory.REFERRAL_BONUS,newUser,referral,referralSetting);
            moneyRecordService.updateMoneyRecord(moneyRecord2);
        }
        
    }

    public Boolean validInfoEdit(StudentOrAdminEdit studentOrAdminEdit, Model model, HttpSession httpSession){
        //get the entity for edit use
        String password = studentOrAdminEdit.getPassword();
        User user = getUserBySession(httpSession);
        //check valid password, whether the email address has been used and trimmed username are blank or not
        Boolean validation1 = passwordMatch(password, user.getPassword(), model);
        Boolean validation2 = (!studentOrAdminEdit.getEmail().equals(user.getEmail()) && emailExist(studentOrAdminEdit.getEmail(), model))? false:true;
        Boolean validation3 = !usernameBlank(studentOrAdminEdit.getUsername(), model);
        return validation1 && validation2 && validation3;
    }

    public void updateInfoTeacher(TeacherEdit teacherEdit, HttpSession httpSession){
        Introduction introduction = introductionService.getIntroductionBySession(httpSession);
        User user = getUserBySession(httpSession);
        user.setEmail(teacherEdit.getEmail().trim());
        user.setUsername(teacherEdit.getUsername().trim());
        introduction.setIntrodution(teacherEdit.getIntroduction());
        userRepository.save(user);
        introductionService.updateIntroduction(introduction);
    }

    public void updateInfoStudentOrAdmin(StudentOrAdminEdit studentOrAdminEdit, HttpSession httpSession){
        User user = getUserBySession(httpSession);
        user.setEmail(studentOrAdminEdit.getEmail().trim());
        user.setUsername(studentOrAdminEdit.getUsername().trim());
        userRepository.save(user);
    }

    public Boolean validNewPassword(PasswordEdit passwordEdit,String hashPassword, Model model){
        Boolean validation = isSamePassword(passwordEdit.getNewPassword(), passwordEdit.getNewPasswordRepeat(), model);
        if(passwordMatch(passwordEdit.getOldPassword(), hashPassword, model)) return false;
        return validation;
    }

    public void updatePassword(PasswordEdit passwordEdit,HttpSession httpSession){
        User user = getUserBySession(httpSession);
        user.setPassword(passwordEncoder.encode(passwordEdit.getNewPassword()));
        userRepository.save(user);
    }

    public void deleteUser(HttpSession httpSession){
        Integer userId = (Integer)httpSession.getAttribute("userId");
        User user = userRepository.findById(userId).orElse(null);
        user.setIsDeleted(true);
        userRepository.save(user);
    }

    public void init(){
        //if there is no admin account, create one.  The default account is admin@gmail.com, password is adminadmin.
        User admin = getAdmin();
        if(admin==null){
            admin = new User();
            admin.setBalance(0);
            admin.setEmail("admin@gmail.com");
            admin.setPassword("$2a$12$fYrGx2KUfqaBKBQsNYQcs.Yowzb8iZQEWwy./8HhSsHRzZySskQ2y");
            admin.setLoginMethod(LoginMethod.email);
            admin.setType(Type.admin);
            admin.setUsername("admin");
            userRepository.save(admin);
        }
    }
}
