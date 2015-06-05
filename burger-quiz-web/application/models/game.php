<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Game extends CI_Model {
  
  function __construct() {
    parent::__construct();
  }
  
  // Get all the Games
  // And their Categories
  function all() {
    
    $data = array();
  
    $q = $this->db->get('games');
    
    foreach($q->result() as $game) {
    
      $qc = $this->db->where('game_id', $game->game_id)->get('categories');
      $game->categories = $qc->result();
      $data[] = $game;
    
    }
    
    return $data;
  
  }

}

/* End of file game.php */
/* Location: ./application/models/game.php */