<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Users extends CI_Controller {

  // Logs in a User
  function signin() {
    
    $this->load->model('user');
    
    if ($data = $this->user->find($this->input->post('login'))) {
      $data['status'] = "ok";
      $this->_set_cookie($data);
      echo json_encode($data);
    }
    else {
      $error = array(
        'status' => 'error',
        'messages' => array(
          'login' => array('message' => 'n\'existe pas')
          ) 
        );

      echo json_encode($error);
    }

  }

  // Register a User
  function signup() {

    $this->load->model('user');

    $data = array(
      'fname' => $this->input->post('fname'),
      'lname' => $this->input->post('lname'),
      'email' => $this->input->post('email')
      );

    $errors = array(
        'status' => 'ok',
        'messages' => array()
      );

    foreach($data as $k => $v) {
      if (strlen($v) == 0) {
        $errors['status'] = 'error';
        $errors['messages'][$k] = array(
          'message' => 'est requis'
          );
      }
    }

    if ($errors['status'] == 'ok' && $id = $this->user->register($data)) {
      if ($id == -1) {
        $errors['status'] = 'error';
        $errors['messages']['email'] = 'est déjà utilisé';
        echo json_encode($errors);
      }
      else {
        $data['user_id'] = $id;
        $this->_set_cookie($data);
        echo json_encode($data);
      }
    }
    else {
      echo json_encode($errors);
    }

  }

  // Logs out a User
  function logout() {
    $this->session->sess_destroy();
    $cookie = array(
        'name'   => 'player',
        'value'  => '',
        'expire' => -216000
      );

    $this->input->set_cookie($cookie);    
    redirect(base_url());
  }

  // Write a cookie
  function _set_cookie($data, $expire = 31536000) {

    $cookie = array(
        'name'   => 'player',
        'value'  => $data['user_id'] . '#' . $data['fname'] . ' ' . $data['lname'],
        'expire' => $expire
      );

    $this->input->set_cookie($cookie);

  }
  
}

/* End of file users.php */
/* Location: ./application/controllers/users.php */