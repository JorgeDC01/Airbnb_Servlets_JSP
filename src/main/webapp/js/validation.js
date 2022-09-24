/**
 * 
 */
 
function validateTel() {
    var err = "Número de teléfono no válido.";
    var regExp = /^([\+][0-9]{1,2})?[-\s]?[0-9]{3}[-\s]?[0-9]{3}[-\s]?[0-9]{3}$/;
    var tel = document.querySelector("#telephone");
	var telErr = document.querySelector("#telErr");
	
	if((tel.value!=="") && (tel.value.match(regExp))){
		if(telErr!==null){
			console.log("Tel correct and exist timeError.!");
			telErr.parentNode.removeChild(telErr);
			tel.classList.remove("errorField");
		}
		console.log("Telephone number is correct.!");
		return true;
	}
	else{
		if(telErr===null){
			console.log("Tel wrong and not exist telError.!");
			var nextElement = document.querySelector("#email");
			var parentElement = nextElement.parentNode;
			var telErr = document.createElement("p");
			telErr.id = "telErr"
			telErr.textContent=err;
			parentElement.insertBefore(telErr,nextElement);
			telErr.classList.add("errorText");
			tel.classList.add("errorField");
		}
		console.log("Telephone number is wrong!.");
		return false;
	}
} 


function validateName() {
    var err = "Nombre en blanco no válido.";
    var regExp = /^\s*$/;
	var name = document.querySelector("#name");
	var nameErr = document.querySelector("#nameErr");
	
	if(!name.value.match(regExp)){
		if(nameErr!==null){
			console.log("Name not empty and exist nameErr.!");
			nameErr.parentNode.removeChild(nameErr);
			name.classList.remove("errorField");
		}
		console.log("Name not empty.!");
		return true;
	}
	else{
		if(nameErr===null){
			console.log("Name wrong and not exist nameError.!");
			var nextElement = document.querySelector("#descDIV");
			var parentElement = nextElement.parentNode;
			var nameErr = document.createElement("p");
			nameErr.id = "nameErr"
			nameErr.textContent=err;
			parentElement.insertBefore(nameErr,nextElement);
			nameErr.classList.add("errorText");
			name.classList.add("errorField");
		}
		console.log("Name empty.!");
		return false;
	}
}


function validateDescription() {
    var err = "Descripcion en blanco no válida.";
    var regExp = /^\s*$/;
	var desc = document.querySelector("#description");
	var descErr = document.querySelector("#descErr");
	
	if(!desc.value.match(regExp)){
		if(descErr!==null){
			console.log("Desc not empty and exist nameErr.!");
			descErr.parentNode.removeChild(descErr);
			desc.classList.remove("errorField");
		}
		console.log("Desc not empty.!");
		return true;
	}
	else{
		if(descErr===null){
			console.log("Desc empty and not exist descErr.!");
			var descElement = document.querySelector("#telephoneDIV");
			var parentElement = descElement.parentNode;
			var descErr = document.createElement("p");
			descErr.id = "descErr";
			descErr.textContent=err;
			parentElement.insertBefore(descErr,descElement);
			descErr.classList.add("errorText");
			desc.classList.add("errorField");
		}
		console.log("Desc empty.!");
		return false;
	}
}

function validateLocalidad() {
    var err = "Localidad en blanco no válida.";
    var regExp = /^\s*$/;
	var localidad = document.querySelector("#localidad");
	var localidadErr = document.querySelector("#localidadErr");
	
	if(!localidad.value.match(regExp)){
		if(localidadErr!==null){
			console.log("Localidad not empty and exist nameErr.!");
			localidadErr.parentNode.removeChild(localidadErr);
			localidad.classList.remove("errorField");
		}
		console.log("Localidad not empty.!");
		return true;
	}
	else{
		if(localidadErr===null){
			console.log("Localidad empty and not exist descErr.!");
			var localidadElement = document.querySelector("#serviceDIV");
			var parentElement = localidadElement.parentNode;
			var localidadErr = document.createElement("p");
			localidadErr.id = "localidadErr";
			localidadErr.textContent=err;
			parentElement.insertBefore(localidadErr,localidadElement);
			localidadErr.classList.add("errorText");
			localidad.classList.add("errorField");
		}
		console.log("Localidad empty.!");
		return false;
	}
}



function validacion(e) {
	console.log("Mouse clicked on submit!");
  if (!validateTel() | !validateName() | !validateDescription() | !validateLocalidad()) {
	  e.preventDefault();
  }
  return true;
}


var button = document.querySelector("#button");
button.addEventListener("click", validacion, false);