var gulp = require("gulp");
var server = require("browser-sync").create();

gulp.task("server", function () {
  server.init({
      server: "src/",
      notify: false,
      open: true,
      cors: true,
      ui: false
});
});