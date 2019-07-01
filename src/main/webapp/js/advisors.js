const urlParams = new URLSearchParams(window.location.search);
const parameterUsername = urlParams.get('user');

// URL must include ?user=XYZ parameter. If not, redirect to homepage.
if (!parameterUsername) {
  window.location.replace('/');
}

//Fetch advisors and requests and display them
    
    function fetchAdvisors(){
      const url = '/advisors';
      fetch(url).then((response) => {
        return response.json();
      }).then((advisors) => {
        const advisorsContainer = document.getElementById('advisors-container');
        

       if(advisors.length == 0){
       advisorsContainer.innerHTML = '<p>You have no advisors yet.</p>';
      }
      else{
       advisorsContainer.innerHTML = 'Your advisors: '; 
      }
      advisors.forEach((advisor) => {  
       const advisorDiv = buildAdvElement(advisor);
       advisorsContainer.appendChild(advisorDiv);
      });

      });
    }

    function buildAdvElement(advString){
     const advElement = document.createElement('p');
     advElement.appendChild(document.createTextNode(advString));
     return advElement;
    }

    // Fetch data and populate the UI of the page.
    function buildUI(){
     fetchAdvisors();
    }