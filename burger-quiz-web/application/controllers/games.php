<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Games extends CI_Controller {

  // Returns all the Games
  // Formatted as JSON including
  // Their Categories
	public function index() {
    
    // Fetching Games from the database
    $this->load->model('game');
    $games = $this->game->all();

		// Echoing JSON
    echo json_encode($games);

	}
  
}

/* End of file games.php */
/* Location: ./application/controllers/games.php */