module Maybe where

import Prelude hiding (Maybe, Nothing, Just)

data Maybe a = Nothing | Just a deriving Show

instance Monad Maybe where
    -- (>>=) :: Maybe a -> (a -> Maybe b) -> Maybe b
    Nothing >>= _ = Nothing
    Just x  >>= f = f x

    -- (>>) :: Maybe a -> Maybe b -> Maybe b
    Nothing >> _ = Nothing
    Just _  >> v = v

    -- return :: a -> Maybe a
    return a = Just a

    -- fail :: String -> Maybe a
    fail _ = Nothing

instance Functor Maybe where
    -- fmap :: (a -> b) -> Maybe a -> Maybe b
    fmap _ Nothing  = Nothing
    fmap f (Just x) = Just (f x)
