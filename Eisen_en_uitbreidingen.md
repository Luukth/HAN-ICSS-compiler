# Eisen en uitbreidingen.

In dit document is opgenomen aan welke eisen het product voldoet en welke uitbreidingen zijn gedaan.



## Aan welke eisen voldoet het product.

Het product voldoet aan alle MUST en SHOULD eisen uit [ASSIGNMENT.md](ASSIGNMENT.md).



## Welke uitbreidingen zijn gedaan.

In tabel 1 is een overzicht welke uitbreidingen zijn gedaan en hoeveel punten die mogelijk opleveren. Bij de meeste uitbreidingen is onder de tabel een voorbeeld gegeven ter verduidelijking.

 

*Tabel 1 - Uitbreidingen met aantal te behalen punten per uitbreiding.*

| #    | Omschrijving                                                 | Aantal punten |
| ---- | ------------------------------------------------------------ | ------------- |
| 1    | Iedere variabele mag alleen een vast type hebben. Dan mag `Var := 10px;` en daarna `Var := 5%;` niet voorkomen. | 5             |
| 2    | Mogelijkheid om extra selectoren toe te voegen. Gescheiden door een komma. | 5             |
| 3    | Checken of er geen booleans worden gebruikt in operaties.    | 5             |
| 4    | Checken of er geen scalaire waardes gebruikt worden als value voor een property. | 5             |
| 5    | Mogelijkheid om deelsommen uit te voeren.                    | 5             |
| 6    | Het is mogelijk om comments en inline comments aan de icss toe te voegen. | 5             |
| 7    | IF-ELSE clause resultaat wordt op de juiste plek (hoogte) in de stylerule geplaatst. | 5             |
| 8    | Checken of er geen properties anders dan width, height, background-color en color worden gebruikt. | 5             |
| 9    | Checken of er geen dubbele declaraties voorkomen tijdens de transformatie. | 5             |
| 10   | Mogelijkheid om warnings aan ASTNodes toe te voegen.         | 5             |
| 11   | Operation checker 100% line coverage.                        | 0             |
| 12   | SymbolTable 100% line coverage.                              | 0             |



**Voorbeeld uitbreiding 2**

Input:

```
p, #profile, .class {
  width: 100px + 100px;
}
```

Output:

```
p, #profile, .class {
  width: 200px;
}
```



**Voorbeeld uitbreiding 3**

```
p {
  width: 100px + TRUE;
}

ERROR: Colors and booleans are not allowed in operations.
```

Checker geeft error als bovenstaande operatie wordt gecheckt.



**Voorbeeld uitbreiding 4**

```
p {
  width: 100;
  height: 5;
}

ERROR: Width value can only be a pixel or percentage literal.
ERROR: Height value can only be a pixel or percentage literal.
```

Checker geeft error omdat value van een property geen scalair waarde mag zijn.



**Voorbeeld uitbreiding 5**

Input:

```
p {
  width: 100px / 2;
  height: 100px / 2 * 2;
}
```

Output:

```
p {
  width: 50px;
  height: 100px;
}
```



**Voorbeeld uitbreiding 6**

```
/*
This is a comment
*/

p { // This is a stylerule.
	width: 100px; // This is a width property with a width of 100px
}
```



**Voorbeeld uitbreiding 7**

Input:

```
p {
	if [TRUE] {
		width: 100px;
	}
	height: 100px;
}
```

Output bij correcte transformatie:

```
p {
  width: 100px; // Uitkomst op juiste hoogte.
  height: 100px;
}
```

Output bij incorrecte transformatie:

```
p {
  height: 100px;
  width: 100px; // Uitkomst van IF clause komt onder andere 	    
                // declaraties in de stylerule.
}
```



**Voorbeeld uitbreiding 8**

```
p {
	breedte: 100px;
}

ERROR: The only properties allowed are height, width, background-color and color.
```

Checker geeft error omdat 'breedte' geen toegestaane property is.



**Voorbeeld uitbreiding 9 & 10**

```
p {
	width: 100px;
	if[TRUE] {
		width: 100px;
	}
}

WARN: Duplicate css properties are declared.
```

Geeft waarschuwing tijdens transformatie omdat dubbele declaratie van width misschien niet zo bedoeld is.

