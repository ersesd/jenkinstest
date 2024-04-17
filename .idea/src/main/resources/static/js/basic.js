let host = 'http://' + window.location.host;

$(document).ready(function () {
  const auth = getToken();
  console.log(auth);
  if(auth === '') {
    // $('#login-true').show();
    // $('#login-false').hide();
    console.log('auth==null')
    window.location.href = host + "/v1/users/login-page";
  } else {
    $('#login-true').show();
    $('#login-false').hide();
  }
})

function logout() {
  // 토큰 삭제
  Cookies.remove('Authorization', { path: '/' });
  window.location.href = host + "/v1/users/login-page";
}

function getToken() {
  let auth = Cookies.get('Authorization');

  if(auth === undefined) {
    return '';
  }

  return auth;
}