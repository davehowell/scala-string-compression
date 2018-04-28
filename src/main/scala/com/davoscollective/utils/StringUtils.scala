package com.davoscollective.utils

import scala.util.matching.Regex
import scala.annotation.tailrec


object StringUtils {
  def main(args: Array[String]): Unit = {

  // This is just something basic to run. There are also tests.
  println("compressStr:")
  println(compressStrFP("aaaabbccddd", 2))
  println(compressStrFP("aaaabbccddd", 3))
  println(compressStrFP("aaaabbccddd", 4))
  println("compressStrShorter:")
  println(compressStrFPShorter("aaaabbccddd", 2))
  println(compressStrFPShorter("aaaabbccddd", 3))
  println(compressStrFPShorter("aaaabbccddd", 4))
  println("compressStr:")
  println(compressStr("aaaabbccddd", 2))
  println(compressStr("aaaabbccddd", 3))
  println(compressStr("aaaabbccddd", 4))
  println("compressStrProcedural:")
  println(compressStrProcedural("aaaabbccddd", 2))
  println(compressStrProcedural("aaaabbccddd", 3))
  println(compressStrProcedural("aaaabbccddd", 4))
  println("regexCompress:")
  println(regexCompress("aaaabbccddd", 2))
  println(regexCompress("aaaabbccddd", 3))
  println(regexCompress("aaaabbccddd", 4))

  }

  /**
   * Performs a naive run-length encoding algorithm to compress a string.
   *
   * Inspired by 99 problems http://aperiodic.net/phil/scala/s-99/
   * It's FP, there's recursion and no mutable var declarations,
   * but it's not tail recursion so might not scale to enormous strings.
   * It also does a lot of string to list conversion in order to take advantage of cons list joining
   * and then a lot of casting back to String when mapping the output
   * Probably unecessary overhead when the signature defines input as a String.
   * A sub-optimal solution
   */
  def compressStrFP(input: String, minOccurency:Int): String = {
    def sublistConsecutiveChars[A](ls: List[A]): List[List[A]] = {
    if (ls.isEmpty) List(List())
    else {
      val (sublist, tailings) = ls span { _ == ls.head }
      if (tailings == Nil) List(sublist)
      else sublist :: sublistConsecutiveChars(tailings)
      }
    }
    sublistConsecutiveChars(input.toList) map { 
      case e if e.length >= minOccurency => e.length.toString + e.head
      case z => z mkString
    } mkString
  }

  /**
   * A shorter version of the FP method. 
   * It removes all the type conversion, and the extra helper function.
   * It is still recursive, but again not tail recursive, so a sub-optimal solution.
   */
  def compressStrFPShorter(input:String, minOccurency:Int): String = {
    if (input.isEmpty) input
    else {
      val (sublist, tailings) = input span { _ == input.head }
      (if (sublist.length >= minOccurency) sublist.length.toString + sublist.head  else sublist) ++ compressStrFPShorter(tailings, minOccurency)
    }
  }

  /**
   * The Solution <<<<<<<<<<--------------------
   * A tail recursive version of the string spanning approach, will scale to very large strings 
   * without blowing the stack.
   * No excessive type conversion, datatype stays a String.
   * There's probably some simpler fold-left way of doing this but I've already over-thought it so I will
   * stop here as the most appropriate solution given the question asks to favour an FP approach
   */
  def compressStr(input:String, minOccurency:Int): String = {
    @tailrec
    def compressor(accum:String, tailingstring: String): String = {
      if (tailingstring.isEmpty) accum
      else {
        val (samechars, tailings) = tailingstring span { _ == tailingstring.head }
        val bite = if (samechars.length >= minOccurency) samechars.length.toString + samechars.head  else samechars 
        compressor(accum ++ bite, tailings)
      }
    }
    if (input.isEmpty) input
    else
      compressor("", input) 
  }

  /**
   * Regex solution to the problem.
   * I think this is the cleanest and easiest to read solution.
   * Very short, and would serialize well across a spark dataframe.
   */
  def regexCompress(input: String, minOccurency:Int): String = {
    //match any word character with repetition of 0 or more times
    val pattern = """(\w)\1{0,}""".r
    (pattern findAllIn input) map {
    case s if s.length >= minOccurency => s.length.toString + s.head
    case s => s
    } mkString
  }


  /**
   * Here's a more Java-style approach to the problem. I would really describe it
   * as imperative, not OOP; because there's nothing inherently OO about mutable variables to
   * store state and using loops for iteration.
   * Controversy: There's no difference between recursion, loops & GOTO statements. They all
   * ultimately manifest as JMP instructions in machine code. If the mutability is contained within
   * a function then it's not purely functional, but there isn't really such a thing anyway.
   * Scala's list reverse method is implemented procedurally for example; the dangerous vars are self-contained.
   */
  def compressStrProcedural(input: String, minOccurency:Int): String = {
    var h = ""
    var bite = StringBuilder.newBuilder
    bite.append(h)
    var output = StringBuilder.newBuilder

    def chew(bite: StringBuilder): Unit = {
      if (bite.length >= minOccurency)
        output.append(bite.length.toString + bite.head)
      else
        output.append(bite)
    }

    for (c <- input) {
      val s = c.toString
      if (s == h)
        bite.append(s)
      else {
        chew(bite)
        bite.length = 0
        bite.append(s)
      }
      h = s
    }
    chew(bite)
    output.toString
  }



}


