package shoeWebshop.controllers;

public class ProductController {


    //---- Nav Links ----\\

    public void changeToHomeView(){
        ChangeView.changeScenes(ChangeView.homeView());
    }

    public void changeToProductView(){
        ChangeView.changeScenes(ChangeView.productView());
    }

    public void changeToReviewView() {
        ChangeView.changeScenes(ChangeView.reviewView());
    }

    public void changeToOrderView(){
        ChangeView.changeScenes(ChangeView.orderView());
    }

    public void changeToLoginView(){
        ChangeView.changeScenes(ChangeView.loginView());
    }
}
