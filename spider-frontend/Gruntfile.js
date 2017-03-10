module.exports = function(grunt) {

  // Project configuration.
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    concat: {
		options: {
			separator: ';',
		},
		dist: {
			src: 'js/appMain/*.js',
			dest: 'build/<%= pkg.name %>_init.js',
		},
	},
	uglify: {
		options: {
			banner: '/*! <%= pkg.name %> <%= grunt.template.today("yyyy-mm-dd") %> */\n'
		},
		dist:{
			files:{
				'build/<%= pkg.name %>_init.min.js':['<%= concat.dist.dest %>']
			}
		}
    }
  });

  // ���ذ��� "uglify" ����Ĳ����
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-uglify');

  // Ĭ�ϱ�ִ�е������б�
  grunt.registerTask('default', ['concat','uglify']);

};