call runcrud.bat
if "%ERRORLEVEL%" == "0" goto startExplorer
echo.
echo runcrud error
goto error

:startExplorer
echo start chrome
start chrome http://localhost:8080/crud/v1/task/getTasks
goto end

:error
echo !! ERROR !!

:end
echo ---=== END ===---