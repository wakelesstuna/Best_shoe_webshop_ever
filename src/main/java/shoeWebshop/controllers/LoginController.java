package shoeWebshop.controllers;

public class LoginController {

    public void changeToHomeView(){
        ChangeView.changeScenes(ChangeView.homeView());
    }

    public void changeToLoginView(){
        ChangeView.changeScenes(ChangeView.loginView());
    }

}
