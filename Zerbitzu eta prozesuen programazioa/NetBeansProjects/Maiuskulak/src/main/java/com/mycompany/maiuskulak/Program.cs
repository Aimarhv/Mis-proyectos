string? line;
while ((line = Console.ReadLine()) != null)
{
    Console.Out.WriteLine(line.ToUpper());
    Console.Out.Flush();
}