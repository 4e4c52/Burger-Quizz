<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Questions extends CI_Controller {

  // Returns the Questions for a
  // Given Category including Answers
  // Formatted as JSON
  public function index($id = 0) {
    
    // Fetching Questions from the database
    $this->load->model('question');
    $questions = $this->question->all($id);

    // Echoing JSON
    echo json_encode($questions);

  }
}

/* End of file questions.php */
/* Location: ./application/controllers/questions.php */