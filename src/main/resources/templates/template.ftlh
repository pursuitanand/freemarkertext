<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sample PDF with Radio Buttons</title>
</head>
<body>
    <h1>User Form</h1>

    <p>Select Your Gender:</p>

    <label>
        <input type="radio" name="gender" value="Male" <#if gender?? && gender == "Male">checked</#if> /> Male
    </label>

    <label>
        <input type="radio" name="gender" value="Female" <#if gender?? && gender == "Female">checked</#if> /> Female
    </label>

    <label>
        <input type="radio" name="gender" value="Other" <#if gender?? && gender == "Other">checked</#if> /> Other
    </label>

     <label>
         <input type="checkbox" name="subscribe" <#if subscribe?? && subscribe>checked</#if> /> Subscribe to newsletter
     </label>

</body>
</html>
