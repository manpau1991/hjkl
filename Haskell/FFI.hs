{-# LANGUAGE ForeignFunctionInterface #-}
module Main (main) where

-- Basic usage of the foreign function interface (FFI)
-- Stolen from the Haskell wiki (6).
-- Thomas Lang, 2016

import Prelude hiding (sin)

-- Get types
import Foreign.C
import Foreign.Ptr (Ptr, nullPtr)

-- This ominous 'foreign ..' line is just a calling
-- convention one must follow

-- Pure function: Take a double, return the sine value
-- of this value, but using the c_sin function, which
-- is in reality the 'sin' function from a C header.
foreign import ccall "sin" c_sin :: CDouble -> CDouble
sin :: Double -> Double
sin d = realToFrac (c_sin (realToFrac d))


-- Similar to the above function.
foreign import ccall "time" c_time :: Ptr a -> IO CTime
getTime :: IO CTime
getTime = c_time nullPtr

    
main :: IO ()
main = do
  print . sin =<< readLn
  print =<< getTime
