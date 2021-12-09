window.onload = async () => {
    let response = await fetch("http://localhost:9000/api/check-session");
    let result = await response.json();

    // go to login page if no session is found
    if(!result.successful)
        window.location.href = "../";

    // return to previous page if role is SALES
    if(result.data.role === "MANAGER")
        window.location.href = "../manager-dashboard"

    // add username and role to screen
    let usernameElem = document.createElement("h2");
    usernameEle.innerText = result.data.username;

    let roleElem = document.createElement("h3");
    roleElem.innerText = result.data.role;

    let userInfo = document.getElementById("user-info");
    userInfo.appendChild(usernameElem);
    userInfo.appendChild(roleElem);
}

async function logout(){
    await fetch("http://localhost:9000/api/logout", {
        method: "DELETE"
    })

    window.location.href = "../"
}