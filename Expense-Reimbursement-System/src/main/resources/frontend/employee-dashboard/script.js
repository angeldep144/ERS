let domain = "http://localhost:9000";

window.onload = async () => {
    let response = await fetch("http://localhost:9000/ers/check-session");
    let result = await response.json();

    // go to login page if no session is found
    if(!result.successful)
        window.location.href = "../";

    // return to previous page if role is manager
    if(result.data.role === "EMPLOYEE")
        window.location.href = "../employee-dashboard"

    // add name to nav-bar
    let userElem = document.getElementById('name');
    userElem.innerText = `Welcome, ${result.data.name}`;

    populateReimbursements(result.data.id);
}

async function populateReimbursements(_id){
    let response = await fetch(`${domain}/ers/user-reimb/${_id}`);
    let data = await response.json();
    let reimbTableBody = document.getElementById('reimb-tbody')
    
    reimbTableBody.innerHTML = "";

    data.forEach(reimb => {
        reimbTableBody.innerHTML += `
        <tr class="reimbursement" id="${reimb.id}">

            <th scope="row">${reimb.id}</th>
            <td class="time-submitted">${reimb.submitted}</td>
            <td class="reimb-author">${reimb.author}</td>
            <td class="reimb-amount">$${reimb.amount}</td>
            <td class="reimb-type">${reimb.type}</td>
            <td class="reimb-status">${reimb.status}</td>
            <td class="reimb-description">${reimb.description == null ? "-" : reimb.description}</td>
            <td class="reimb-receipt">${reimb.receipt == null ? "-" : `<img src= ${reimb.receipt} />`}</td>
            <td class="reimb-resolver">${reimb.resolver == null ? "-" : reimb.resolver}</td>

        </tr>
        `
    });
}

async function createReimbursement(){
    let response = await fetch("http://localhost:9000/ers/check-session");
    let result = await response.json();

    let _id = result.data.id;

    let _amount = document.getElementById("reimb-amount").value;
    let _type = document.getElementById("reimb-type").value;
    let _description = document.getElementById("reimb-description").value;
    let _receipt = document.getElementById("reimb-receipt") == null ? null : document.getElementById("reimb-receipt").value;

    console.log(_receipt);
    console.log(_receipt.src);


    await fetch(`http://localhost:9000/ers/user-reimb/${_id}`, {
        method: "POST",
        body: JSON.stringify({
            reimb_id: _id,
            amount: _amount,
            description: _description,
            img_src: _receipt.src,
            author_id: _id,
            type_id: _type
        })
    })

    populateReimbursements(id);
}


async function logout(e){
    await fetch("http://localhost:9000/ers/logout", {
        method: "DELETE"
    })

    window.location.href = "../"
}

/* function show_image(img)
{
  document.write("img src="+img+">");
} */