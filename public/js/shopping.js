"use strict";

class SaleItem {

    constructor(product, quantity) {
        // only set the fields if we have a valid product
        if (product) {
            this.product = product;
            this.quantityPurchased = quantity;
            this.salePrice = product.listPrice;
            //this.productID = product.productID;
        }
    }

    getItemTotal() {
        return this.salePrice * this.quantityPurchased;
    }

    getProd() {
        return this.product;
    }

    getQuantity() {
        return this.quantityPurchased;
    }

    changeQuantity(quantity) {
        this.quantityPurchased = this.quantityPurchased + quantity;
    }

}

class ShoppingCart {

    constructor() {
        this.saleItemList = new Array();
    }

    reconstruct(sessionData) {
        for (let item of sessionData.saleItemList) {
            this.addItem(Object.assign(new SaleItem(), item));
        }
    }

    getItems() {
        return this.saleItemList;
    }

    addItem(item) {
        if (this.saleItemList.length !== 0) {
            for (let check of this.saleItemList) {
                if (item.getProd().productID === check.getProd().productID) {
                    check.changeQuantity(item.getQuantity());
                    return;
                }
            }
        }

        if (item.getProd().quantity - item.getQuantity() >= 0) {
            this.saleItemList.push(item);
            return true;
        } else {
            alert("You have ordered more products than there are in stock");
            return false;
        }
    }

    setCustomer(customer) {
        this.purchasedBy = customer;
    }

    getTotal() {
        let total = 0;
        for (let item of this.saleItemList) {
            total += item.getItemTotal();
        }
        return total;
    }

}



var module = angular.module('ShoppingApp', ['ngResource', 'ngStorage']);
module.config(function ($sessionStorageProvider, $httpProvider) {
   // get the auth token from the session storage
   let authToken = $sessionStorageProvider.get('authToken');

   // does the auth token actually exist?
   if (authToken) {
      // add the token to all HTTP requests
      $httpProvider.defaults.headers.common.Authorization = 'Basic ' + authToken;
   }
});


module.factory('productDAO', function ($resource) {
    return $resource('/api/products/:id');
});

module.factory('categoryDAO', function ($resource) {
    return $resource('/api/categories/:cat');
});

module.factory('registerDAO', function ($resource) {
    return $resource('/api/register');
});

module.factory('signInDAO', function ($resource) {
    return $resource('/api/customers/:username');
});

module.factory('saleDAO', function ($resource) {
    return $resource('/api/sales');
});


module.factory('cart', function ($sessionStorage) {
    let cart = new ShoppingCart();

    // is the cart in the session storage?
    if ($sessionStorage.cart) {

        // reconstruct the cart from the session data
        cart.reconstruct($sessionStorage.cart);
    }

    return cart;
});

module.controller('ProductController', function (productDAO, categoryDAO) {
    // load the products
    this.products = productDAO.query();
    // load the categories
    this.categories = categoryDAO.query();

    this.selectCategory = function (selectedCat) {
        this.products = categoryDAO.query({"cat": selectedCat});
    };

    this.selectAll = function () {
        this.products = productDAO.query();
    };
});

module.controller('CustomerController', function (registerDAO, signInDAO, $sessionStorage, $window, $http) {
    this.registerCustomer = function (customer) {
        registerDAO.save(null, customer);
    };

    this.signInMessage = "Please sign in to continue.";

    let ctrl = this;

    this.signIn = function (username, password) {
        // generate authentication token
        let authToken = $window.btoa(username + ":" + password);

        // store token
        $sessionStorage.authToken = authToken;

        // add token to the sign in HTTP request
        $http.defaults.headers.common.Authorization = 'Basic ' + authToken;
        
        // get customer from web service
        signInDAO.get({'username': username},
        
        // success
        function (customer) {
            // also store the retrieved customer
            $sessionStorage.customer = customer;
            // redirect to home
            $window.location.href = '.';
        },
        
        // fail
        function () {
            ctrl.signInMessage = 'Sign in failed. Please try again.';
        }
        );
    };

    this.checkSignIn = function () {
        if ($sessionStorage.customer) {
            this.signedIn = true;
            this.welcome = "Welcome " + $sessionStorage.customer.firstName;
        } else {
            this.signedIn = false;
        }
    };

    this.signOut = function () {
        delete $sessionStorage.customer;
        delete $sessionStorage.cart;
        delete $sessionStorage.selectedProduct;
        $window.location.href = 'index.html';
    };
});

module.controller('ShoppingCartController', function (cart, $sessionStorage, $window, saleDAO) {
    this.items = cart.getItems();
    this.total = cart.getTotal();
    this.selectedProduct = $sessionStorage.selectedProduct;

    this.add = function (product) {
        $sessionStorage.selectedProduct = product;

        $window.location.href = 'product_page.html';
    };

    this.addToCart = function (quantity) {
        let saleItem = new SaleItem(this.selectedProduct, quantity);

        this.added = cart.addItem(saleItem);
        $sessionStorage.cart = cart;

        if (this.added) {
            alert(this.selectedProduct.name + " was added to your cart");
            $window.location.href = 'view_products.html';
        }
    };



    this.checkOut = function () {
        this.customer = $sessionStorage.customer;

        cart.setCustomer(this.customer);

        if (cart.getItems().length === 0) {
            alert("Cannot check out with an empty cart");
        } else {
            console.log(cart);

            saleDAO.save(cart);

            delete $sessionStorage.cart;

            $window.location.href = 'thankyou.html';
        }
    };

});