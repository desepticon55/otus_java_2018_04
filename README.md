
# Домашняя работа для курса "Разработчик Java" в Otus.ru

Группа 2018-04

### Студент
Alexey Bodyak (Алексей Бодяк)

desepticon555@gmail.com

# ДЗ

### Homework 1
ДЗ 01. Сборка и запуск проекта
Создать проект под управлением maven в Intellij IDEA. 

Добавить зависимость на Google Guava/Apache Commons/библиотеку на ваш выбор.
Использовать библиотечные классы для обработки входных данных.

Задать имя проекта (project_name) в pom.xml 
Собрать project_name.jar содержащий все зависимости.
Проверить, что приложение можно запустить из командной строки.

Выложить проект на github. 

Создать ветку "obfuscation" изменить в ней pom.xml, так чтобы сборка содержала стадию обфускации байткода.

### Homework 2
Написать стенд для определения размера объекта. Определить размер пустой строки и пустых контейнеров. Определить рост размера контейнера от количества элементов в нем.


Например:
Object — 8 bytes,
Empty String — 40 bytes
Array — from 12 bytes

В коде класса ArrayCopyTutor (возьмите из материалов) реализуйте метод deleteAnimal(int position).
В классе StringTutor (в материалах) реализуйте метод checkGreeting.

### Homework 3
ДЗ 03. MyArrayList
Написать свою реализацию ArrayList на основе массива. Проверить, что на ней работают методы java.util.Collections

Дополнительно можно реализовать HashMap

### Homework 4

ДЗ 04. Измерение активности GC
Написать приложение, которое следит за сборками мусора и пишет в лог количество сборок каждого типа (young, old) и время которое ушло на сборки в минуту.
Добиться OutOfMemory в этом приложении через медленное подтекание по памяти (например добавлять элементы в List и удалять только половину).
Настроить приложение (можно добавлять Thread.sleep(...)) так чтобы оно падало с OOM примерно через 5 минут после начала работы.
Собрать статистику (количество сборок, время на сборрки) по разным типам GC.

### Homework 5

ДЗ 05. Тестовый фреймворк на аннотациях
Написать свой тестовый фреймворк. Поддержать аннотации @Test, @Before, @After. 
Запускать вызовом статического метода с 
1. именем класса с тестами, 
2. именем package в котором надо найти и запустить тесты
