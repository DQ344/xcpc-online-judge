$dirs = @("cpp11", "cpp17", "csharp-latest", "gcc11", "go-latest", "java8", "java11", "java17", "javascript-latest", "python2", "python3")

foreach ($dir in $dirs) {
    Start-Process -NoNewWindow powershell -ArgumentList "docker build -t sandbox-$dir ." -WorkingDirectory $dir
}
