module.exports = function(grunt) {

  // Project configuration.
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    jsResources: [
      'bower_components/angular/angular.js',
      'bower_components/angular-aria/angular-aria.js',
      'bower_components/angular-route/angular-route.js',
//      'bower_components/angular-resource/angular-resource.js',
      'bower_components/angular-animate/angular-animate.js',
      'bower_components/angular-material/angular-material.js'
//      'bower_components/angular-cookies/angular-cookies.js',
//      'bower_components/angular-filter/dist/angular-filter.js',
//      'bower_components/angular-loading-bar/build/loading-bar.js',            
//      'bower_components/momentjs/moment.js',
//      'bower_components/angular-material-datetimepicker/js/angular-material-datetimepicker.js',
//      'bower_components/mdPickers/dist/mdPickers.js'
    ],

    uglify: {
      options: {
        banner: '/*! <%= pkg.name %> \n <%= pkg.author %> \n Compressed java script files of all required angular files. \n <%= grunt.template.today("yyyy-mm-dd") %> */\n'
      },
      build: {
        src: [ '<%= jsResources %>' ],
        dest: 'js/ngAdmin.min.js'
      }
    },
  
  watch: {
    options: {
      livereload: true
    }
  },

  express: {
      all: {
        options: {
          port: 9000,
          hostname: 'localhost',
          bases: ['.'],
          livereload: true,
        }
      }
    },

  prettify: {
    options: {
      "indent": 4,
      "condense": true,
      "indent_inner_html": true,
      "unformatted": [
        "a",
        "pre"
      ]
    },
    all: {
    expand: true,       
    src: ['pages/*.html'],
    dest: ''
  },
  }

  });

  grunt.loadNpmTasks('grunt-contrib-connect');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-parallel');
  grunt.loadNpmTasks('grunt-express');
  grunt.loadNpmTasks('grunt-prettify');

  // Default task(s).
  grunt.registerTask('server', [ 'uglify', 'express', 'prettify', 'watch']);

};