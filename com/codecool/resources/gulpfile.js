var gulp = require("gulp");
var server = require("browser-sync").create();
var concat = require('gulp-concat');
var csso = require("gulp-csso");
var postcss = require("gulp-postcss");
var autoprefixer = require("autoprefixer");
var del = require('del');

gulp.task("css", function () {
	return gulp.src(['static/css/*.css', 'static/css/blocks/*.css', '!static/css/style.css'])
		.pipe(csso())
		.pipe(postcss([
			autoprefixer()
		]))
		.pipe(concat('style.css'))
		.pipe(gulp.dest('static/css'));
});

gulp.task('clean', function () {
	return del('static/css/style.css');
});

gulp.task("server", function () {
  server.init({
      server: "static/",
      notify: false,
      open: true,
      cors: true,
      ui: false
});
});

gulp.watch(["static/css/**/*.css", '!static/css/style.css'], gulp.series('clean', 'css'));