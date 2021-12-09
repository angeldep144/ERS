window.onload(e) = async () => {
    let response = await fetch("http://localhost:9000/api/check-session");
    let result = await response.json();

    if(result.successful){
        window.location.href = `${result.data.role_id}-dashboard`
    }
}

async function login(e){
    e.preventDefault(); // this prevent form onsubmit from refreshing

    let usernamInputElem = document.getElementById("username-input");
    let passwordInputElem = document.getElementById("password-input");

    let response = await fetch("http://localhost:9000/api/login", {
        method: "POST",
        body: JSON.stringify({
            username: usernameInputElem.value,
            password: passwordInputElem.value
        })
    })

    let result = await response.json();

    // define what role id is: 1 for employee and 2 for manager

    if(result.successful){
       window.location.href = `${result.data.role_id}-dashboard`
    } else {
        // display some kind of alert for unsuccessful login
    }
}