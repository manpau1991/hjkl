
module Main (main) where

import Data.List          (sortBy, insertBy, sort, group)
import Control.Arrow      (second, (&&&))
import Data.Ord           (comparing)
import System.Environment (getArgs)


-- Find the frequency of a char in the passed string and aggregate the result data.
freq :: Ord a => [a] -> [(Int, a)]
freq = map (length &&& head) . group . sort


-- Takes a list of frequencies and associated characters and returns the Huffman encoding.
-- The returned list contains each individual character in the original string exactly once
-- and associated Huffman encodings as a String.
huffman :: [(Int, Char)] -> [(Char, String)]
huffman = reduce . map (\(p, c) -> (p, [(c, "")])) . sortBy (comparing fst)
  where
    -- Add counts (relevant for list insertion), get 'left' and 'right' tree branches right.
    add (p1, xs1) (p2, xs2) = (p1 + p2, map (second ('0':)) xs1 ++ map (second ('1':)) xs2)

    reduce [(_,xs)] = sortBy (comparing fst) xs
    reduce (x1:x2:xs) = reduce $ insertBy (comparing fst) (add x1 x2) xs


-- Compute and print result.
encode :: String -> IO ()
encode = mapM_ (\(a, b) -> putStrLn ('\'' : a : "\' : " ++ b)) . huffman . freq 

-- Main.
main :: IO ()
main = getArgs >>= \a -> encode (head a)

