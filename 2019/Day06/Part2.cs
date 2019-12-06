using System;
using System.Collections.Generic;
using System.IO;

namespace Day6
{
    public class Body
    {
        public string name;
        public Body parent;
    }

    public class Day6
    {
        public static void Main(string[] args)
        {
            Dictionary<string, Body> bodiesLookup = new Dictionary<string, Body>();
            List<Body> bodies = new List<Body>();
            StreamReader read = new StreamReader("./input.txt");
            Body result, newBody;

            while (read.Peek() != -1)
            {
                string orbit = read.ReadLine();
                if (!bodiesLookup.TryGetValue(orbit.Substring(4), out result))
                {
                    newBody = new Body {name = orbit.Substring(4)};
                    bodiesLookup.Add(orbit.Substring(4), newBody);
                }
                else
                {
                    newBody = result;
                }
                
                if (bodiesLookup.TryGetValue(orbit.Substring(0, 3), out result))
                {
                    newBody.parent = result;
                }
                else
                {
                    newBody.parent = new Body {name = orbit.Substring(0, 3)};
                    bodiesLookup.Add(orbit.Substring(0,3), newBody.parent);
                }

               bodies.Add(newBody);
            }

            List<Body> steps = new List<Body>();
            Body currentY = bodiesLookup["YOU"].parent;
            Body currentS = bodiesLookup["SAN"].parent;

            while(currentY.name != "COM" && currentS.name != "COM")
            {
                if (steps.Contains(currentY))
                {
                    steps.Remove(currentY);
                    while (steps.Contains(currentY.parent))
                    {
                        steps.Remove(currentY.parent);
                        currentY = currentY.parent;
                    }
                    break;
                }

                if (steps.Contains(currentS))
                {
                    steps.Remove(currentS);
                    while (steps.Contains(currentS.parent))
                    {
                        steps.Remove(currentS.parent);
                        currentS = currentS.parent;
                    }
                    break;
                }
                steps.Add(currentY);
                steps.Add(currentS);
                currentY = currentY.parent;
                currentS = currentS.parent;
            }

            Console.WriteLine(steps.Count);
        }
    }
}
