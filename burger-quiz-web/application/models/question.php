<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Question extends CI_Model {
  
  function __construct() {
    parent::__construct();
  }
  
  // Get all of the Question
  // Of a given Category with
  // Their answers
  function all($category_id) {
    
    $data = array();
  
    $q = $this->db->where('category_id', $category_id)->get('questions');
    
    foreach($q->result() as $question) {
    
      $qc = $this->db->where('question_id', $question->question_id)->get('answers');
      $question->answers = $qc->result();
      $data[] = $question;
    
    }
    
    return $data;
  
  }

  // Find and returns a Question
  function find($id) {

    $q = $this->db->where('question_id', $id)->get('questions');
    return $q->row();

  }

}

/* End of file question.php */
/* Location: ./application/models/question.php */