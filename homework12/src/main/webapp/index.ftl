<html>
<head><title>FreeMarker Hello World</title>

<body>
<form name="user" action="hello" method="post">
    Id: <input type="text" name="id" /> <br/>
    Name: <input type="text" name="name" /> <br/>
    Age: <input type="text" name="age" />       <br/>
    <input type="submit" value="Save" />
</form>

<table class="datatable">
    <tr>
        <th>ID</th>  <th>Name</th> <th>Age</th>
    </tr>
    <#list employees as employee>
    <tr>
        <td>${employee.id}</td> <td>${employee.name}</td><td>${employee.age}</td>
    </tr>
    </#list>
</table>
</body>
</html>
