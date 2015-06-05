<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class User extends CI_Model {
  
  function __construct() {
    parent::__construct();
  }

  // Find a user by email
  function find($email) {

    $q = $this->db->where('email', $email)->get('users');
    return $q->row_array();

  }
  
  // Save a new User
  function register($data) {

    $time = date("Y/m/d H:i:s");
    $data['created_at'] = $time;
    $data['updated_at'] = $time;

    if (! $this->_is_uniq($data['email'])) return -1;

    if ($this->db->insert('users', $data)) {
      return $this->db->insert_id();
    }

    return false;
  
  }

  // Check if the email address is uniq
  function _is_uniq($email) {

    $this->db->where('email', $email);
    $this->db->from('users');

    if ($this->db->count_all_results() == 0) return true;

    return false;

  }

}

/* End of file user.php */
/* Location: ./application/models/user.php */