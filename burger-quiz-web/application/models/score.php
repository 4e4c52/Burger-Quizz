<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Score extends CI_Model {
  
  function __construct() {
    parent::__construct();
  }
  
  // Save a new score
  function insert($user, $category, $miams) {

    $time = date("Y/m/d H:i:s");
    
    $data = array(
      'user_id' => $user,
      'category_id' => $category,
      'miams' => $miams,
      'created_at' => $time,
      'updated_at' => $time
      );

    $this->db->insert('scores', $data);
    return $this->db->insert_id();
  
  }

  // Fetch $limit records from the database
  function all($limit = 50) {

    $scores = array();

    $q = $this->db->limit($limit)->order_by('miams', 'desc')->get('scores');

    foreach ($q->result() as $score) {
      
      $q = $this->db->where('user_id', $score->user_id)
                    ->get('users');

      $q2 = $this->db->where('category_id', $score->category_id)
                     ->get('categories');

      $score->user = $q->row();
      $score->category = $q2->row();

      $scores[] = $score;

    }

    return $scores;

  }

}

/* End of file score.php */
/* Location: ./application/models/score.php */