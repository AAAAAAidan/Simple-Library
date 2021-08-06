
// Classes

class ValidationField {
  constructor(value, regex) {
    this.value = value;
    this.regex = regex;
  }
}

// Functions

function validate() {
  var valid = true;
  var alphanumericRegex = /[A-Za-z0-9]+/g;
  var emailRegex = /[A-Za-z0-9]+@+[A-Za-z0-9]+.+[A-Za-z]+/g;
  var formFields = {
    "username": new ValidationField(document.getElementById("username").value.trim(), alphanumericRegex),
    "email": new ValidationField(document.getElementById("email").value.trim(), emailRegex),
    "password": new ValidationField(document.getElementById("password").value.trim(), alphanumericRegex),
    "passwordConfirm": new ValidationField(document.getElementById("passwordConfirm").value.trim(), alphanumericRegex),
  }

  for (formField in formFields) {
    var field = formFields[formField];

    if (!field["value"].match(field["regex"])) {
      if (formField != "passwordConfirm") {
        alert("The value provided for " + formField + " must be alphanumeric.");
      }

      valid = false;
    }
  }

  if (formFields["password"].value != formFields["passwordConfirm"].value) {
    alert("The passwords entered do not match.");
    valid = false;
  }

  if (valid) {
    alert("Signed up successfully! Redirecting to the home page.");
    return true;
  }
  else {
    return false;
  }
}
