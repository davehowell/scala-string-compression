# scala-string-compression

This is a collection of functions that perform the same naive run-length encoding algorithm on a given string.  
The project uses sbt as the build tool, and contains some basic unit tests using scalatest.

I've included a couple of FP oriented solutions with a tail recursive one that I am putting forward as the solution, given that "a functional style is highly encouraged". I've also included a procedural solution, and a regular expression based solution, which is probably the cleanest.

The project follows these guidelines:


## EXERCISE NOTES: 

The exercise should be completed in Scala.
The code should be written into a Github or BitBucket private repos
Once completed, you will need to provide the BT hiring manager with read permission to review the solution.
A functional style is highly encouraged, if desired multiple solutions can be completed (e.g. one functional, one OO.)

## Requirements:

1.  Implement a function to compress an input string as below:


        def compressStr(input:String, minOccurency:Int):String= ???


The function will compress a string for some characters, which are not less than 'minOccurency' in serial as a substring in the string.

For example:

        compressStr("ABBCDD", 2) == "A2BC2D"

        compressStr("ABBBCCDDD", 3) == "A3BCC3D"

        compressStr("ABBBCCDDD", 4) == "ABBBCCDDD"

2. Provide some unit tests if possible
