import Control.Monad (MonadPlus(..))

-- Definition of a custom list datatype.
data List a = Empty 
            | Cons a (List a)

-- implementation of List Monad
instance Monad List where
    -- return :: a -> List a
    return x = Cons x Empty
    -- (>>=) :: List a -> (List b) -> List b
    xs >>= f = conc (fmap f xs)
    -- (>>) :: List a -> List b -> List b
    xs >> ys = conc (fmap (\_ -> ys) xs)
    -- fail :: String -> List a
    fail _ = Empty

instance MonadPlus List where
    -- mzero :: List a
    mzero = Empty
    -- mplus :: List a -> List a -> List a
    mplus = app

-- implementation of Functor
-- fmap == map
instance Functor List where
    fmap _ Empty = Empty
    fmap f (Cons x xs) = Cons (f x) (fmap f xs)

instance Show a => Show (List a) where
    -- show :: List a -> String
    show l = "[" ++ showList l ++ "]"
        where showList :: (Show a) => List a -> String
              showList Empty = ""
              showList (Cons x Empty) = show x
              showList (Cons x xs) = show x ++ "," ++ showList xs
             
-- foldr for List
foldR :: (a -> b -> b) -> b -> List a -> b
foldR _ v Empty = v
foldR f v (Cons x xs) = f x (foldR f v xs)

-- Append operator (++)
app :: List a -> List a -> List a
app Empty ys = ys
app (Cons x xs) ys = Cons x (app xs ys)

-- Concatenation operator (`concat`)
conc :: List (List a) -> List a
conc = foldR (\xs ys -> xs `app` ys) Empty

-- Convert Haskell list to List
toList :: [a] -> List a
toList = foldr Cons Empty

-- Convert List to Haskell list
fromList :: List a -> [a]
fromList = foldR (:) []

-- A list comprehension similar to [(x,y) | x <- xs, y <- ys]
list_comp :: List a -> List b -> List (a, b)
list_comp xs ys = do {x <- xs; y <- ys; return (x, y)}

-- example list
list1 :: List Int
list1 = toList [1..5]
list2 :: List Int
list2 = toList [6..10]
list3 :: List Char
list3 = toList ['A'..'C']
