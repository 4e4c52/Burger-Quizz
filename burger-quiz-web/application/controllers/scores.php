<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Scores extends CI_Controller {

  // Returns the 50 firsts Scores
  // Including their User
  // Formatted as JSON 
  public function index() {
    
    // Fetching Games from the database
    $this->load->model('score');
    $scores = $this->score->all();

    // Echoing JSON
    echo json_encode($scores);

  }

  // Update the Score of the current User
  // in the Session Object
  // Called from Javascript
  function update() {

    $question = $this->input->post('question');
    $answer = $this->input->post('answer');
    $category = $this->input->post('category');
    $user = $this->input->post('user_id');
    $time = $this->input->post('time');

    if (! $this->session->userdata('miams')) $miams = 0;
    else $miams = $this->session->userdata('miams');

    $good_answer = $this->_get_answer($question);

    if ($good_answer == $answer) {
      $speed = 100 - $time;
      $miams += (1 + ceil($speed / 10));
    }

    $data = array(
      'user_id'     => $user,
      'question'    => $question,
      'answer'      => $answer,
      'category'    => $category,
      'miams'       => $miams,
      'good_answer' => $good_answer
    );

    $this->session->set_userdata($data);

    // Echoing JSON
    echo json_encode($data);

  }

  // Save the Score of the Current User
  // Called from Javascript
  function save() {

    $user = $this->session->userdata('user_id');
    $category = $this->session->userdata('category');
    $miams = $this->session->userdata('miams');

    if ($miams > 0) {
      $this->load->model('score');
      $id = $this->score->insert($user, $category, $miams);
      $this->session->set_userdata('miams', 0);
      echo json_encode(array('score_id' => $id));
    }

  }

  // Return the good answer id
  // _ means not accessible via URI
  // (Kind of private)
  function _get_answer($question_id) {

    $this->load->model('question');
    $question = $this->question->find($question_id);
    return $question->good_answer;

  }

}

/* End of file scores.php */
/* Location: ./application/controllers/scores.php */