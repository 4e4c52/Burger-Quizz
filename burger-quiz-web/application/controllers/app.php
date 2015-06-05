<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class App extends CI_Controller {

  // Index Page (Login Form)
  public function index() {

    $this->load->model('category');
    $categories = $this->category->all();

    $tpl = array(
      "head" => array(
          "title" => ""
        ),
      "data" => array(
        'categories' => $categories
        ),
      "view" => "app/scores"
      );

    $this->load->view('template', $tpl);

  }

  // Games Page
	public function games() {

		$this->load->model('category');
    $categories = $this->category->all();

    $tpl = array(
      "head" => array(
          "title" => ""
        ),
      "data" => array(
        'categories' => $categories
        ),
      "view" => "app/games"
      );

		$this->load->view('template', $tpl);

	}

  // Quiz Page
  public function questions() {

    $this->load->model('category');
    $categories = $this->category->all();

    $tpl = array(
      "head" => array(
          "title" => ""
        ),
      "data" => array(
        'categories' => $categories
        ),
      "view" => "app/questions"
      );

    $this->load->view('template', $tpl);

  }

  // Scores Page
  public function scores() {

    $this->load->model('category');
    $categories = $this->category->all();

    $tpl = array(
      "head" => array(
          "title" => ""
        ),
      "data" => array(
        'categories' => $categories
        ),
      "view" => "app/scores"
      );

    $this->load->view('template', $tpl);

  }

}

/* End of file app.php */
/* Location: ./application/controllers/app.php */