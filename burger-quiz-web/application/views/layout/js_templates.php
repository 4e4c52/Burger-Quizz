<!-- End Of Application Stage -->
</section>

<!-- Games Template -->
<script type="text/html" id="games-view">
<div class="page-header">
  <h1>
    Jeux
    <small>Choisissez un jeu et une catégorie</small>
  </h1>
</div>
<div id="games" class="accordion"></div>
</script>

<!-- Game Template -->
<script type="text/html" id="game-view">
<div class="accordion-heading">
  <a href="#game-<%= model.game_id %>" data-parent="#games" data-toggle="collapse" class="accordion-toggle">
  <%= model.name %>
  </a>
</div>
<div class="accordion-body collapse" id="game-<%= model.game_id %>">
  <div class="accordion-inner">
  <table class="table table-stripped">
    <thead>
      <tr>
        <th>Catégorie</th>
        <th>Jouer</th>
      </tr>
    </thead>
    <tbody>
    <% _.each(model.categories, function(category) { %> 
      <tr data-category="<%= category.category_id %>">
        <td><%= category.name %></td>
        <td data-category="<%= category.category_id %>">
          <button class="btn btn-primary" id="play-category-<%= category.category_id %>" data-category="<%= category.category_id %>">
            <i class="icon-play icon-white"></i> Jouer
          </button>
        </td>
      </tr>
    <% }); %>
    </tbody>
  </table>
  </div>
</div>
</script>

<!-- Questions View -->
<script type="text/html" id="questions-view">
<div>
  <div>
    <p class="pull-left">
      <img src="<?= base_url(); ?>assets/images/icons/podium.png" alt="" />
      &nbsp;<strong id="miams">0 miam</strong>
    </p>
    <p class="pull-right">
      <img src="<?= base_url(); ?>assets/images/icons/clock.png" alt="" />
      &nbsp;<strong id="remaining-time"></strong>
    </p>
  </div>
  <br />
  <br />
  <div class="progress progress-success progress-striped active" 
       style="margin-bottom: 9px;">
    <div class="bar" style="width: 0%" id="timer"></div>
  </div>
  <div id="question-stage"></div>
</div>
</script>

<!-- Question Template -->
<script type="text/html" id="question-view">
<blockquote>
  <p class="question"><%= model.text %></p>
</blockquote>
<div class="answers">
  <% _.each(model.answers, function(answer) { %>
    <p>
      <button class="btn btn-inverse btn-large" 
              data-id="<%= answer.answer_id %>">
        <%= answer.text %>
      </button>
    </p>
  <% }); %>
</div>
</script>

<!-- Scores View -->
<script type="text/html" id="scores-view">
<div class="page-header">
  <h1>
    Top 50
    <small>
    Afficher uniquement la catégorie 
    <select name="filter" id="filter">
      <option value="0">Générale</option>
      <?php 
      foreach($categories as $category) {
        echo '<option value="' . $category->category_id . '">';
        echo $category->name;
        echo '</option>';
      }
      ?>
    </select>
    </small>
  </h1>
</div>
<table class="table table-striped table-bordered">
  <thead>
    <tr>
      <td>Classement</td>
      <td>Prénom</td>
      <td>Nom</td>
      <td>Catégorie</td>
      <td>Score</td>
    </tr>
  </thead>
  <tbody id="scores">
    
  </tbody>
</table>
</script>

<!-- Score View -->
<script type="text/html" id="score-view">
<td class="<%= model.score_id %>">#<%= rank %></td>
<td><%= model.user.fname %></td>
<td><%= model.user.lname %></td>
<td><%= model.category.name %></td>
<td><%= model.miams %></td>
</script>

<!-- Login View -->
<script type="text/html" id="login-view">
<div class="row-fluid">
  <form class="span5 well" action="" method="post" id="signup-form">
    <fieldset>
      <legend><strong>Inscription</strong></legend>
      <div class="control-group">
        <label class="control-label" for="fname">Prénom</label>
        <div class="controls">
          <div class="input-prepend">
            <span class="add-on">P</span><input type="text" class="input-xlarge" id="fname" name="fname" placeholder="Utilisé pour les scores" />
          </div>
        </div>
      </div>
      <div class="control-group">
        <label class="control-label" for="lname">Nom</label>
        <div class="controls">
          <div class="input-prepend">
            <span class="add-on">N</span><input type="text" class="input-xlarge" id="lname" name="lname" placeholder="Utilisé pour les scores" />
          </div>
        </div>
      </div>
      <div class="control-group">
        <label class="control-label" for="email">Adresse email</label>
        <div class="controls">
          <div class="input-prepend">
            <span class="add-on">@</span><input type="email" class="input-xlarge" id="email" name="email" placeholder="Servira à vous connecter" />
          </div>
        </div>
      </div>
    </fieldset>
    <div class="form-actions">
      <input type="submit" id="signup" value="S'enregistrer" class="btn btn-success btn-large" />
    </div>
  </form>
  <div class="span2 or-box">
    <h2>OU</h2>
  </div>
  <form class="span5 well" action="" method="post" id="signin-form">
    <fieldset>
      <legend><strong>Connexion</strong></legend>
      <div class="control-group">
        <label class="control-label" for="login">Adresse email</label>
        <div class="controls">
          <div class="input-prepend">
            <span class="add-on">@</span><input type="email" class="input-xlarge" id="login" name="login" placeholder="Pas de mot de passe requis" />
          </div>
        </div>
      </div>
    </fieldset>
    <div class="form-actions">
      <input type="submit" id="signin" value="Connexion" class="btn btn-primary btn-large" />
    </div>
  </form>
</div>
</script>