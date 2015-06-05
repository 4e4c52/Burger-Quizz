<?php
$this->load->view('layout/header.tpl.php', $head);
$this->load->view($view, $data);
$this->load->view('layout/js_templates.php', $data); 
$this->load->view('layout/footer.tpl.php'); 