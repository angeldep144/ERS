let domain = "http://localhost:9000";

window.onload = async () => {
    let response = await fetch("http://localhost:9000/ers/check-session");
    let result = await response.json();

    // go to login page if no session is found
    if(!result.successful)
        window.location.href = "../";

    // return to previous page if role is SALES
    if(result.data.role === "MANAGER")
        window.location.href = "../manager-dashboard"

    // add name to nav-bar
    let userElem = document.getElementById('name')
    userElem.innerText = `Welcome, ${result.data.name}`;


    populateReimbursements();
}

var _resolver_id = getId();

async function getID(){
    let response = await fetch("http://localhost:9000/ers/check-session");
    let result = await response.json();

    return result.data.id; // resolver id gotten from current session
}

async function populateReimbursements(){
    let response = await fetch(`${domain}/ers/reimb`);
    let data = await response.json();

    let reimbTableBody = document.getElementById('reimb-tbody')
    reimbTableBody.innerHTML = "";

    data.forEach(reimb => {
        reimbTableBody.innerHTML += `
        <tr class="reimbursement" id="${reimb.id}">

            <th scope="row" value="@item.QueueName">${reimb.id}</th>
            <td class="time-submitted">${reimb.submitted}</td>
            <td class="reimb-author">${reimb.author}</td>
            <td class="reimb-amount">$${reimb.amount}</td>
            <td class="reimb-type">${reimb.type}</td>
            <td class="reimb-status">${reimb.status}</td>

            <td class="reimb-description">${reimb.description == null ? "-" : reimb.description}</td>

            <td class="reimb-receipt">${reimb.receipt == null ? "-" : `<img src= ${reimb.receipt} />`}</td>
            
            <td class="reimb-btn">
                <button type="button" class="btn btn-success" id="approve-btn-${reimb.id}" onclick="approve(event)"> Approve </button>
                <button type="button" class="btn btn-danger" id="reject-btn-${reimb.id}" onclick="reject(event)"> Reject </button>
            </td>

        </tr>
        `
    });
}

async function approve(e) {
    let _reimb_id = e.target.id.slice("approve-btn-".length,e.target.id.length) // id of reimbursement gotten from button
    
    // debugging
    console.log('Approving reimbursement request with ID: ' + _reimb_id);

    await fetch(`${domain}/ers/reimb/${_reimb_id}`,{
        method: "PATCH",
        body: JSON.stringify({
            reimb_id: _reimb_id,
            status_id: 1,
            resolver_id: _resolver_id
        })
    })

    populateReimbursements();
}

async function reject(e) {
    let _reimb_id = e.target.id.slice("reject-btn-".length,e.target.id.length) // id of reimbursement gotten from button
    
    // debugging
    console.log('Rejecting reimbursement request with ID: ' + _reimb_id);

    await fetch(`${domain}/ers/reimb/${_reimb_id}`,{
        method: "PATCH",
        body: JSON.stringify({
            reimb_id: _reimb_id,
            status_id: 2,
            resolver_id: _resolver_id
        })
    })

    populateReimbursements();
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

// **********************************************************************

/* function show_image(event) {
 
    // Access image by id and change the display property to block

    document.getElementById('image')
            .style.display = "block";

    document.getElementById('img-btn')
            .style.display = "none";
} */