<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Category extends CI_Model {
  
  function __construct() {
    parent::__construct();
  }
  
  // Get all the Categories
  function all() {
    
    $data = array();
  
    $q = $this->db->get('categories');
    
    return $q->result();
  
  }

}

/* End of file category.php */
/* Location: ./application/models/category.php */