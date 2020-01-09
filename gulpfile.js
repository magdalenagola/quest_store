var gulp = require("gulp");
var babel = require("gulp-babel");
var rename = require("gulp-rename");

gulp.task("js", function () {
  return gulp.src("src/js/app.js")
    .pipe(babel({
        presets: ['env']
        }))
    .pipe(rename("index.js"))
    .pipe(gulp.dest("src/js"));
});