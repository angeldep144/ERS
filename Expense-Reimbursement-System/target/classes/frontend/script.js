let domain = "http://localhost:9000";

window.addEventListener("load", async () => {
    console.log('in check-session');
    let response = await fetch(`${domain}/ers/check-session`);
    let result = await response.json();

    console.log(result.successful);

    if(result.successful){
        window.location.href = `./${result.data.role}-dashboard`
    }
})

async function login(e){
    e.preventDefault(); // this prevent form onsubmit from refreshing

    let usernameInputElem = document.getElementById("username-input");
    let passwordInputElem = document.getElementById("password-input");

    console.log(usernameInputElem);
    console.log(passwordInputElem);

    console.log(`username: ${usernameInputElem.value}`);
    console.log(`password: ${passwordInputElem.value}`);

    let response = await fetch(`${domain}/ers/login`, {
        method: "POST",
        body: JSON.stringify({
            username: usernameInputElem.value,
            password: passwordInputElem.value
        })
    })

    let result = await response.json();

    // define what role id is: 1 for employee and 2 for manager

    if(result.successful){
       window.location.href = `./${result.data.role.toLowerCase()}-dashboard`
    } else {
        console.log("Unsuccessful login");
        alert("Wrong username or password")
    }
}