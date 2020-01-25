var gulp = require("gulp");
var server = require("browser-sync").create();
var concat = require('gulp-concat');
var csso = require("gulp-csso");
var postcss = require("gulp-postcss");
var autoprefixer = require("autoprefixer");

gulp.task("css", function () {
	return gulp.src(['src/css/*.css', 'src/css/blocks/*.css'])
		.pipe(csso())
		.pipe(postcss([
			autoprefixer()
		]))
		.pipe(concat('style.css'))
		.pipe(gulp.dest('src/css'));
});

gulp.task("server", function () {
  server.init({
      server: "src/",
      notify: false,
      open: true,
      cors: true,
      ui: false
});
});

gulp.watch(["src/css/**/*.css", '!src/css/style.css'], gulp.series('css'));