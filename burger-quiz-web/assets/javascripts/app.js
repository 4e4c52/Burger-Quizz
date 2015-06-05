// Initializer
App = {
  posX: 0,
  posY: 0,
  maxY: 0,
  timer: null,
  user_id: 0,
  user_name: "",
  user_score_id: 0,

  // Start the application
  start: function() {
    // Create the router
    App.router = new App.Router();
    // Bind all events to the router
    App.router.on('all', function(e) {
      this.cleanUp(this.currentRoute);
      this.currentRoute = e.split(':')[1];
      this.checkLoginStatus();
    });

    // Bindings nav links events
    $('.navbar').delegate('a', 'click', function(e) {
      // App.showLoader();
      $('.active').removeClass('active');
      $(this).parent().addClass('active');
      var path = $(this).attr('href');
      path = path.replace(App.baseURI, '');

      if (path == 'users/logout') {
        return true;
      }
      else if (path.indexOf(App.router.currentRoute) == -1) {
        if (App.router.currentRoute != 'scores') {
          e.preventDefault();
          App.router.navigate(path, { trigger: true });
        } else {
          return true;
        }
        
      }     
        
    });

  },

  // Start the animation with the burger
  startAnimation: function() {
    App.maxY = App.getSize().y - 154
    App.posX = 0;
    App.posY = App.maxY;
    $('body').css('height', App.getSize().y + 'px');
    $('body').css('background-position', App.posX + 'px ' + App.maxY + 'px');
    App.timer = setInterval(App.move, 100);
  },

  // Move the burger
  move: function() {
    App.posX += 50;
    if (App.posX > App.getSize().x) clearInterval(App.timer);
    App.posY = App.maxY - (Math.cos(App.posX) * Math.cos(App.posX) * 300);
    $('body').css('background-position', App.posX + 'px ' + App.posY + 'px');
  },

  // Get Viewport size
  getSize: function() {
    return { x: $(window).width(), y: $(window).height() };
  },
  
  // Show and hide the loader image
  showLoader: function() { $('#loader').show(); },
  hideLoader: function() { $('#loader').hide(); }
};
  
// App Router
App.Router = Backbone.Router.extend({
  
  // All the routes (URIs) of the application
  routes: {
    '': 'home',
    'app/games': 'gameSelection',
    'app/questions/:id': 'questions',
    'app/scores': 'scores', 
  },

  currentRoute: '',

  // Handle the Home route (/)
  home: function() {
    App.loginView = new App.LoginView();
    App.loginView.render();
  },
  
  // Handle the Game Selection route
  gameSelection: function() {
    App.games.fetch();
    App.gamesView = new App.GamesView();
    App.gamesView.render();
  },

  // Handle the Quiz
  questions: function(id) {
    App.questions.category = id;
    App.questions.fetch();
    App.questionsView = new App.QuestionsView();
    App.questionsView.render();   
  },

  // Handle the Score Board view
  scores: function() {
    App.scores.fetch();
    App.scoresView = new App.ScoresView();
    App.scoresView.render();
  },

  // Callbacks for cleaning the DOM
  cleanUp: function(route) {
    switch(route) {
      case 'scores':
        App.scoresView.cleanUp();
        break;
      case 'questions':
        App.questionsView.cleanUp();
        break;
      case 'gameSelection':
        App.gamesView.cleanUp();
        break;
      case 'home':
        App.loginView.cleanUp();
        break;
    }
  },

  // Check the login status
  // And log in the user if
  // There is a cookie
  checkLoginStatus: function() {
    if (App.user_id == 0) {
      // Redirect user to login form if he's not logged in
      var cookie = $.cookie('player');
      if (cookie != null && App.user_id == 0) {
        var data = cookie.split('#');
        App.user_id = data[0];
        App.user_name = data[1].replace(/\+/g, ' ');
        $('#nav-user').text(App.user_name);
        $('#nav-logout').show();
        App.router.navigate('app/games', { trigger: true });
      } else {
        App.router.navigate('', { trigger: true }); 
      }
    }
    else if (App.user_id != 0 && this.currentRoute == 'home') {
      // Redirect if the user is logged in and is on the login page
      $('#nav-logout').show();
      App.router.navigate('app/games', { trigger: true });
    }
  },
    
});
  
/*** Games ***/

// A Game
App.Game = Backbone.Model.extend({});
  
// Games Collection
App.Games = Backbone.Collection.extend({
    
  model: App.Game,
  url: function() { return App.baseURI + 'games'}
    
});
App.games = new App.Games();

// The View Containing One Game
App.GameView = Backbone.View.extend({
  
  tagName: 'div',
  className: 'accordion-group',
  
  // View events
  events: {
    'click .btn': 'play'
  },
  
  // Constructor
  initialize: function() {
    this.template = _.template($('#game-view').html());
    this.render();
  },
  
  // Rendering function
  render: function() {
    var output = this.template({ model: this.model.toJSON() });
    $(this.el).append(output);
  },
  
  // Event callback
  play: function(e) {
    App.showLoader();
    var id = $(e.target).data('category');
    App.router.navigate('app/questions/' + id, { trigger: true });
  }
    
});
  
// The View Containing the Games
App.GamesView = Backbone.View.extend({

  // Constructor
  initialize: function() {
    this.template = _.template($('#games-view').html());
    App.games.on('reset', this.renderGames, this);
  },
  
  // Render the view
  render: function() {
    $(this.el).html(this.template({}));
    $('#stage').html(this.el);
  },
  
  // Render all the games
  renderGames: function() {
    
    _.each(App.games.models, function(game) {
      var view = new App.GameView({ model: game });
      this.$('#games').append(view.el);
    }, this);
    
    App.hideLoader();
    
  },

  // Callback to clean the DOM
  cleanUp: function() {
    this.off();
    this.remove();
  }

});

/*** Question ***/

// A Question
App.Question = Backbone.Model.extend({});
  
// Games Collection
App.Questions = Backbone.Collection.extend({

  category: 0,
    
  model: App.Question,
  url: function() { 
    return App.baseURI + 'questions/index/' + this.category; 
  }
    
});
App.questions = new App.Questions();

// The View Containing One Question
App.QuestionView = Backbone.View.extend({
  
  tagName: 'div',
  className: 'well',
  
  // Constructor
  initialize: function() {
    this.template = _.template($('#question-view').html());
    this.render();
  },  
  
  // Rendering function
  render: function() {
    var output = this.template({ model: this.model.toJSON() });
    $(this.el).append(output);
  }
      
});

// The View Containing the Questions
App.QuestionsView = Backbone.View.extend({
  
  currentIndex: 0,
  currentTime: 0,
  currentQuestion: null,
  maxTime: 0,
  timer: null,
  sleep: null,
  sleeping: false,
  communicating: false,

  // View events
  events: {
    'click .answers button': 'submitAnswer'
  },
  
  // Constructor
  initialize: function() {
    // Bind the context (this) inside the functions...
    _.bindAll(this, 'countdown', 'wake');

    this.template = _.template($('#questions-view').html());
    App.questions.on('reset', this.renderQuestion, this);
    this.currentIndex = 0;
  },
  
  // Render the view
  render: function() {
    $(this.el).html(this.template({}));
    $('#stage').html(this.el);
  },
  
  // Render a question
  renderQuestion: function() {

    this.currentQuestion = App.questions.models[this.currentIndex];
    this.maxTime = this.currentQuestion.get('timer');
    this.timer = setInterval(this.countdown, 100);

    var view = new App.QuestionView({ model: this.currentQuestion });
    this.$('#question-stage').html(view.el);
    
    App.hideLoader();  
  },

  // Go to the next question
  nextQuestion: function() {
    this.currentTime = 0;
    this.currentIndex++;

    if (this.currentIndex > App.questions.models.length - 1) {
      this.endGame();
    } else {
      this.renderQuestion();
    }
  },

  // Submit the answer to the server
  submitAnswer: function(e) {
    if ( e != null && $(e.target).hasClass('disabled')) return false;

    this.$el.find('.btn').addClass('disabled');

    var that, question_id, answer_id;
    that = this; // Binding context
    question_id = this.currentQuestion.get('question_id');

    if (e === undefined || e === null) {
      answer_id = 0;
    } else {
      answer_id = $(e.target).data('id');
    }

    if (!this.communicating) {

      this.communicating = $.ajax({
        type: 'post',
        url: App.baseURI + 'scores/update',
        dataType: 'json',
        data: {
          question: question_id,
          answer: answer_id,
          category: App.questions.category,
          user_id: App.user_id,
          time: ((100 * this.currentTime) / this.maxTime)
        },
        beforeSend: function() {
          clearInterval(that.timer);
          $(that.el).find('#timer').parent().addClass('progress-success').removeClass('progress-danger');
          $(that.el).find('#timer').css('width', '0')
        },
        success: function(json) {
          if (json.miams > 1) $('#miams').text(json.miams + ' miams');
          else $('#miams').text(json.miams + ' miam');

          that.$el.find('button[data-id=' + json.good_answer + ']')
                  .removeClass('btn-inverse')
                  .addClass('btn-success');
          that.$el.find('button[data-id!=' + json.good_answer + ']')
                  .removeClass('btn-inverse')
                  .addClass('btn-danger')
                  .delay(1000)
                  .fadeOut();

          that.sleep = setInterval(that.wake, 1500);
          this.communicating = false;
        },
        error: function(error) {
          console.log('An error occured...', error);
        }
      });

    }
    
  },

  // End the current game
  endGame: function() {

    App.showLoader();
    App.startAnimation();
    this.currentIndex = 0;
    this.currentTime = 0;
    this.currentQuestion = null;

    $.post(App.baseURI + 'scores/save', function(json) {
      var data = $.parseJSON(json);
      App.user_score_id = data.score_id;
      App.router.navigate('app/scores', { trigger: true });
    });

  },

  // Manages the countdown
  countdown: function() {
    var currentTime = this.currentTime + 0.1;
    var barPercent = ((100 * currentTime) / this.maxTime);
    var remaining = Math.ceil(this.maxTime - currentTime);

    (remaining > 1) ? remaining += ' secondes restantes...' : remaining += ' seconde restante...';

    $(this.el).find('#timer').css('width', barPercent + '%');
    if (barPercent >= 50 && barPercent < 75) {
      $(this.el).find('#timer').parent().addClass('progress-warning').removeClass('progress-success');
    }
    else if (barPercent >= 75) {
      $(this.el).find('#timer').parent().addClass('progress-danger').removeClass('progress-warning');
    }

    $(this.el).find('#remaining-time').text(remaining);
    
    this.currentTime = currentTime;

    if (currentTime >= this.maxTime) {
      $(this.el).find('#timer').parent().removeClass('active');
      if (!this.communicating) this.submitAnswer();
    }
  },

  // Wake up the UI after a sleep
  wake: function() {
    if (this.sleeping) {
      clearInterval(this.sleep);
      this.sleeping = false;
      this.communicating = false;
      this.nextQuestion();
    }
    else this.sleeping = true;
  },

  // Cleanup the DOM
  cleanUp: function() {
    this.off();
    this.remove();    
  }

});

/*** Scores ***/

// A Score
App.Score = Backbone.Model.extend({});
  
// Scores Collection
App.Scores = Backbone.Collection.extend({
    
  model: App.Score,
  url: function() { return App.baseURI + 'scores'}
    
});
App.scores = new App.Scores();

// The View Containing One Score
App.ScoreView = Backbone.View.extend({
  
  tagName: 'tr',
  
  // Constructor
  initialize: function() {
    this.template = _.template($('#score-view').html());
    this.render();
  },
  
  // Render
  render: function() {
    var output = this.template({ 
      model: this.model.toJSON(), 
      rank: this.options.rank 
    });
    this.$el.append(output);
  }
    
});
  
// The View Containing the Scores
App.ScoresView = Backbone.View.extend({
  
  // Constructor
  initialize: function() {
    this.template = _.template($('#scores-view').html());
    App.scores.on('reset', this.renderScores, this);
  },

  events: {
    'change #filter': 'filterScores'
  },
  
  // Rendering function
  render: function() {
    this.$el.html(this.template({}));
    $('#stage').html(this.el);
  },
  
  // Render all the scores
  renderScores: function() {
    _.each(App.scores.models, function(score, index) {
      var view = new App.ScoreView({ model: score, rank: index + 1 });
      this.$('#scores').append(view.el);
    }, this);
    var css = { 
      "font-weight": "bold", 
      "font-size": "1.2em", 
      "color": "#3A87AD" }
    $('.' + App.user_score_id).parent().css(css);
    App.hideLoader();    
  },

  filterScores: function() {

    var category = $('#filter option:selected').val();

    var scores = _.filter(App.scores.models, function(score) { 
      if (category == 0) return true;
      return score.get('category_id') == category; 
    });

    this.$el.find('#scores').html('');

    _.each(scores, function(score, index) {
      var view = new App.ScoreView({ model: score, rank: index + 1 });
      this.$('#scores').append(view.el);
    }, this);

  },

  // Cleanup the DOM
  cleanUp: function() {
    this.off();
    this.remove();    
  }

});

/*** Home Page (Login Form) ***/

// The View Containing the Forms
App.LoginView = Backbone.View.extend({

  // View events
  events: {
    'click #signup': 'signup',
    'click #signin': 'signin'
  },
  
  // Constructor
  initialize: function() {
    this.template = _.template($('#login-view').html());
  },
  
  // Render the view
  render: function() {
    $(this.el).html(this.template({}));
    $('#stage').html(this.el);
    App.hideLoader();
  },

  // Manages the signup form
  signup: function(e) {
    e.preventDefault();
    var data = $('#signup-form').serialize();

    $.ajax({
      url: App.baseURI + 'users/signup',
      type: 'post',
      data: data,
      dataType: 'json',
      beforeSend: function() {
        App.showLoader();
        $(e.target).addClass('disabled').attr('value', 'Enregistrement...');
      },
      success: function(json) {
        if (json.status == 'error') {
          App.hideLoader();

          // Cleaning old error messages
          $('.error').removeClass('error');
          $('.help-inline').remove();

          // Inserting new error messages
          _.each(json.messages, function(v, k) {
            $('#' + k).after('<span class="help-inline">' + v.message + '</span>').parentsUntil('fieldset').addClass('error');
          });

          $(e.target).removeClass('disabled').attr('value', "Enregistrement");
        }
        else {
          $('#nav-user').text(json.fname + ' ' + json.lname);
          App.user_id = json.user_id;
          App.user_name = json.fname + ' ' + json.lname;
          App.router.navigate('app/games', { trigger: true });
        }
      },
      error: function(error) {
        App.hideLoader();
        $(e.target).removeClass('disabled').attr('value', "Enregistrement");
      }
    });
  },

  // Manages the signin form
  signin: function(e) {
    e.preventDefault();
    var data = $('#signin-form').serialize();

    $.ajax({
      url: App.baseURI + 'users/signin',
      type: 'post',
      data: data,
      dataType: 'json',
      beforeSend: function() {
        App.showLoader();
        $(e.target).addClass('disabled').attr('value', 'Connexion...');
      },
      success: function(json) {
        if (json.status == 'error') {
          App.hideLoader();

          // Cleaning old error messages
          $('.error').removeClass('error');
          $('.help-inline').remove();

          // Inserting new error messages
          _.each(json.messages, function(v, k) {
            $('#' + k).after('<span class="help-inline">' + v.message + '</span>').parentsUntil('fieldset').addClass('error');
          });

          $(e.target).removeClass('disabled').attr('value', "Connexion");
        }
        else {
          $('#nav-user').text(json.fname + ' ' + json.lname);
          App.user_id = json.user_id;
          App.user_name = json.fname + ' ' + json.lname;
          App.router.navigate('app/games', { trigger: true });
        }
      },
      error: function(error) {
        App.hideLoader();
        $(e.target).removeClass('disabled').attr('value', "Connexion");
      }
    });
  },

  // Cleanup the DOM
  cleanUp: function() {
    this.off();
    this.remove();    
  }

});