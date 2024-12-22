const shell = require("shelljs");

// Copy index.html to 404.html
if (shell.cp("build/index.html", "build/404.html").code !== 0) {
    shell.echo("Error: Failed to copy index.html to 404.html");
    shell.exit(1);
}
