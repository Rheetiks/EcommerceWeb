

function displayPostApi(input, url) {
    async function callApi() {
        result = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(input)
        });

        const contentType = result.headers.get('Content-Type');
        if (contentType && contentType.includes('application/json')) {
            return result.json(); // Parse JSON data
        }
        return result.text();
    }
    return callApi();
}

function displayGetApi(url) {
    async function callApi() {
        result = await fetch(url);
        result = await result.json();
        return result
    }
    return callApi();
}

function displayPostApiText(input, url) {
    async function callApi() {
        result = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(input)
        });
        result = await result.text();
        return result;
    }
    return callApi();
}





function cancel() {
    document.querySelector("#loginpage").style.display = "none";
}

function displayLogin() {
    window.location.href = "/Html/Login.html";
}

function signUpPage() {
    document.querySelector("#loginbox").style.display = "none";
    document.querySelector("#SignUpbox").style.display = "block";
    document.body.style.backgroundColor = "#9A616D";
}

function displaySellerRegister() {
    window.location.href = "/html/SellerLogin.html";
}

function displaySellerLogin() {
    document.getElementById("SignUpbox").style.display = "none";
    document.getElementById("loginbox").style.display = "block";

}


function displayProducts() {
    document.querySelector(".products-container").style.display = "flex";
    document.querySelector(".images-container").style.display = "none";
    let productlist = document.getElementById("main-products-list");
    displayGetApi("http://localhost:8088/products").then(function display(val) {
        console.log(val);
        document.getElementById("main-products-list").innerHTML = val.map((product) =>
            // console.log(product.productId)
            `<div class="card"  id="${product.productId}" onclick="productDetails(${product.productId})" style="min-width: 20rem;max-width: 20rem; height: 500px; margin: 20px 10px 5px 20px;">
                <img class="card-img-top" height="300px" src="http://localhost:8088/ProductImages/${product.productUrl}"
                alt="Card image cap">
                <div class="card-body">
                <h5 class="card-title">${product.productName}</h5>
                <p class="card-text">${product.productDescription}</p><br>
                    <h5>Price: ${product.productAmount}</h5>
                </div>
            </div>`).join("");
    });
}

function browseProducts() {
    window.location.href = "/Html/Main.html";
}





function registerUser() {
    let user = {};
    user.userName = document.getElementById("user-register-name").value
    user.userEmail = document.getElementById("user-register-email").value
    user.userPassword = document.getElementById("user-register-password").value
    user.userPhoneNo = document.getElementById("user-register-phone").value
    user.userAddress = document.getElementById("user-register-address").value
    console.log(user);

    displayPostApiText(user, "http://localhost:8088/user/addUser").then(function display(val) {
        console.log(val);
        alert(val)
        if (val != "Successfully Registered User") {
            return
        }
        window.location.href = "/Html/Login.html";

    })

}
function loginUser() {
    let user = {};
    user.userEmail = document.getElementById("user-login-email").value;
    user.userPassword = document.getElementById("user-login-password").value;
    displayPostApi(user, "http://localhost:8088/auth/user/login").then(function display(val) {
        console.log(val);
        if (val == "Credentials Invalid !!") {
            alert("Invalid User Name or password");
            return;
        }
        alert("Seccessfully Logged In");
        window.location.href = "/Html/Main.html";
        // document.getElementById("main-cart-btn").style.display="block";
        // document.getElementById("main-orders-btn").style.display="block";
        // document.getElementById("main-login-btn").style.display="none";

    });


}





function registerSeller() {
    let seller = {};
    seller.sellerName = document.getElementById("seller-register-name").value;
    seller.sellerEmail = document.getElementById("seller-register-email").value;
    seller.sellerPassword = document.getElementById("seller-register-password").value;
    seller.sellerPhoneNo = document.getElementById("seller-register-phone").value;
    console.log(seller);
    displayPostApi(seller, "http://localhost:8088/seller/addSeller").then(function display(val) {
        console.log(val);
        if (val == "Email Already Exists") {
            alert(val)
            return;
        }
        alert(val);
        displayLogin();
        // window.location.href="/Html/Main.html";
    })
}

function sellerLogin() {
    let seller = {};
    seller.userEmail = document.getElementById("seller-login-email").value;
    seller.userPassword = document.getElementById("seller-login-password").value;

    console.log(seller);

    displayPostApi(seller, "http://localhost:8088/auth/seller/login").then(function display(val) {
        console.log(val);
        if (val == "Credentials Invalid !!") {
            alert("Invalid User Name or password");
            return;
        }
        alert("Seccessfully Logged In");
        window.location.href = "/Html/Main.html";
    })

}








function securedPostApi(input, url, role) {
    async function callApi() {
        let jwtToken = await getJWTTokenFromCookie(role);
        result = await fetch(url, {
            method: 'POST',
            headers: {
                Authorization: `Bearer ${jwtToken}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(input)
        });

        const contentType = result.headers.get('Content-Type');
        if (contentType && contentType.includes('application/json')) {
            return result.json();
        }
        return result.text();
    }
    return callApi();
}

function securedGetApi(url, role) {
    // headers.append('Authorization', `Bearer ${jwtToken}`);
    async function callApi() {
        let jwtToken = await getJWTTokenFromCookie(role);
        result = await fetch(url, {
            method: 'GET',
            headers: {
                Authorization: `Bearer ${jwtToken}`,
                'Content-Type': 'application/json'
            },
            credentials: 'include'

        });
        const contentType = result.headers.get('Content-Type');
        if (contentType && contentType.includes('application/json')) {
            return result.json();
        }
        return result.text();
    }
    return callApi();
}


function doSomething() {
    securedGetApi("http://localhost:8088/user/test", "userCookie").then(function display(val) {
        console.log(val)
    });
}

function getJWTTokenFromCookie(cookieName) {
    const cookieValue = document.cookie
        .split('; ')
        .find((row) => row.startsWith(`${cookieName}=`));

    if (cookieValue) {
        return cookieValue.split('=')[1];
    }

    return null;
}


function sellerProducts() {
    let productContainer = document.querySelector(".products-list");
    securedGetApi("http://localhost:8088/seller/viewProducts", "sellerCookie").then(function display(val) {
        console.log(val);
    })
}


function addProductPage() {
    window.location.href = "http://localhost:8088/Html/SellerAddProduct.html";
}

function sellerProducts() {
    window.location.href = "http://localhost:8088/Html/SellerProducts.html";
}


function productDetails(productId) {
    localStorage.setItem("productId", productId);
    window.location.href = "http://localhost:8088/Html/ProductDetails.html";
}


function getProductsByCategory(categoryName, id,categoryContId) {
    let category = {};
    category.categoryName = categoryName;
    displayPostApi(category, "http://localhost:8088/getProductByCategory").then(function display(val) {
        document.getElementById(id).innerHTML = val.map((product) =>
            // console.log(product.productId)
            `<div class="card"  id="${product.productId}" onclick="productDetails(${product.productId})" style="min-width: 20rem;max-width: 20rem; height: 500px; margin: 20px 10px 5px 20px;">
                <img class="card-img-top" height="300px" src="http://localhost:8088/ProductImages/${product.productUrl}"
                alt="Card image cap">
                <div class="card-body">
                <h5 class="card-title">${product.productName}</h5>
                <p class="card-text">${product.productDescription}</p><br>
                    <h5>Price: ${product.productAmount}</h5>
                </div>
            </div>`).join("");
        let categoryCont = document.getElementById(categoryContId);
        let category = categoryCont.getElementsByTagName("a");
        for (const anchorTag of category) {
            if (anchorTag.getAttribute('value') === categoryName) {
                anchorTag.classList.add('active');
                
            }else{
                anchorTag.classList.remove('active');

            }
        }

    })
}

function addToCart(productId){
    let product={};
    product.productId=productId;
    securedPostApi(product,"http://localhost:8088/user/addToCart","userCookie").then(function display(val){
        alert(val);
    })
}

function goToCart(){
    window.location.href="http://localhost:8088/Html/UserCart.html";
}

function removeFromCart(productId){
    let product={};
    product.productId=productId;
    securedPostApi(product,"http://localhost:8088/user/removeFromCart","userCookie").then(function display(val){
        alert(val);
        window.location.href="http://localhost:8088/Html/UserCart.html";
    })
}

function updateQuantity(productId){
    let cart={};
    cart.productId=productId;
    quantity=document.getElementById(productId);
    cart.cartProductQuantity=quantity.value;
    console.log(cart);

    securedPostApi(cart,"http://localhost:8088/user/setCartProductQuantity","userCookie").then(function display(val){
        if(val=="Quantity Exceeded"){
            quantity.value-=1;
            alert(val)
        }
    })
}


function placeOrder(){
    securedGetApi("http://localhost:8088/user/placeOrder","userCookie").then(function display(val){
        alert(val);
    })
}
