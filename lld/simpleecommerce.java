
class simpleecommerce {

    class User {
        String userId;
        boolean loginStatus;
        String password;

        protected boolean verifyLogin() {
            return true;
        }
    }

    class Customer extends User {
        String name;
        String address;
        String cardInfo;
        String shippingInfo;

        // protected boolean register() {
        // // register
        // }

        // protected boolean updateProfile() {
        // // updateProfile
        // }

        // protected boolean addtoCart() {
        // // addtocart
        // }

        // protected boolean placeorder() {
        // // place order directly without adding to cart
        // }
    }

    class ShoppingCart {
        String cardId;
        String productId;
        int quantity;
        String dateAdded;

        protected void checkOut() {
            //
        }

    }

    public static void main(String[] args) {

    }
}