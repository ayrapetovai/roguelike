@echo off

call java -cp ../roguelike.jar;../libs/* -Djava.library.path=../natives ru.ayeaye.game.main.MainClass
