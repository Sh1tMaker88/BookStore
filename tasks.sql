/*1 - Найдите номер модели, скорость и размер жесткого диска для всех ПК
стоимостью менее 500 дол. Вывести: model, speed и hd*/
SELECT model, speed, hd
FROM pc
WHERE price < 500;

/*2 - Найдите производителей принтеров. Вывести: maker*/
SELECT DISTINCT maker
FROM product
WHERE type='Printer';

/*3 - Найдите номер модели, объем памяти и размеры экранов ПК-блокнотов,
цена которых превышает 1000 дол.*/
SELECT model, ram, screen
FROM laptop
WHERE price > 1000;

/*4 - Найдите все записи таблицы Printer для цветных принтеров.*/
SELECT *
FROM printer
WHERE color='y';

/*5 - Найдите номер модели, скорость и размер жесткого диска ПК, имеющих
12x или 24x CD и цену менее 600 дол*/
SELECT model, speed, hd
FROM pc
WHERE cd IN ('12x', '24x');

/*6 - Укажите производителя и скорость для тех ПК-блокнотов, которые имеют
жесткий диск объемом не менее 100 Гбайт*/
SELECT maker, speed
FROM laptop INNER JOIN product
	USING(model)
WHERE hd > 100;

/*7 - Найдите номера моделей и цены всех продуктов (любого типа),
выпущенных производителем B (латинская буква).*/
SELECT pc.model, pc.price
FROM pc JOIN product 
	USING(model)
WHERE maker LIKE 'B%'
UNION
SELECT laptop.model, laptop.price
FROM laptop JOIN product 
	USING(model)
WHERE maker LIKE 'B%'
UNION
SELECT printer.model, printer.price
FROM printer JOIN product 
	USING(model)
WHERE maker LIKE 'B%';

-- rework!

/*8 - Найдите производителя, выпускающего ПК, но не ПК-блокноты*/
SELECT maker
FROM product 
WHERE type='PC' AND maker NOT IN (
	SELECT maker
    FROM product
    WHERE type='laptop'
    )
GROUP BY maker;

/*9 - Найдите производителей ПК с процессором не менее 450 Мгц. Вывести:
Maker*/
/*Изменил частоту на 4500 ибо процессоров ниже 450 не добавлял*/
SELECT DISTINCT maker
FROM product JOIN pc
	USING(model)
GROUP BY speed
HAVING speed > 4500;

/*10 - Найдите принтеры, имеющие самую высокую цену. Вывести: model, price*/
SELECT model, price 
FROM printer
HAVING price IN
	(
	SELECT MAX(price)
    FROM printer
    );
	
/*11 - Найдите среднюю скорость ПК*/
SELECT ROUND(AVG(speed)) AS average_speed
FROM pc;

/*12 - Найдите среднюю скорость ПК-блокнотов, цена которых превышает 1000*/
SELECT ROUND(AVG(speed))
FROM laptop
WHERE price > 1000;

/*13 - Найдите среднюю скорость ПК, выпущенных производителем A*/
SELECT maker, ROUND(AVG(speed))
FROM pc JOIN product
	USING(model)
GROUP BY maker
HAVING maker='AMD';

/*14 - Для каждого значения скорости найдите среднюю стоимость ПК с такой
же скоростью процессора. Вывести: скорость, средняя цена*/
SELECT speed, ROUND(AVG(price)) AS average_speed
FROM pc
GROUP BY speed;

/*15 - Найдите размеры жестких дисков, совпадающих у двух и более PC. Вывести: HD*/
SELECT hd
FROM pc
group by hd
HAVING COUNT(hd) > 1;

/*16 - Найдите пары моделей PC, имеющих одинаковые скорость и RAM. В
результате каждая пара указывается только один раз, т.е. (i,j), но не (j,i),
Порядок вывода: модель с большим номером, модель с меньшим номером,
скорость и RAM*/
SELECT pc1.model, pc2.model, pc1.speed, pc1.ram
FROM pc AS pc1, pc AS pc2
WHERE pc1.speed=pc2.speed AND pc1.ram=pc2.ram AND pc1.code > pc2.code;

/*17 - Найдите модели ПК-блокнотов, скорость которых меньше скорости
любого из ПК. Вывести: type, model, speed*/
SELECT type, model, speed
FROM laptop JOIN product
	USING(model)
WHERE speed < 
	(
    SELECT ROUND(AVG(speed))
    FROM pc
    );

/*18 - Найдите производителей самых дешевых цветных принтеров. Вывести:
maker, price*/
SELECT DISTINCT maker, price
FROM printer JOIN product
	USING(model)
WHERE price IN
	(
    SELECT MIN(price)
    FROM printer
    WHERE color='y'
    ) 
AND color IN ('y');

/*19 - Для каждого производителя найдите средний размер экрана выпускаемых
им ПК-блокнотов. Вывести: maker, средний размер экрана*/
SELECT maker, AVG(screen) AS average_screen
FROM laptop JOIN product
	USING(model)
GROUP BY maker;

/*20 - Найдите производителей, выпускающих по меньшей мере три различных
модели ПК. Вывести: Maker, число моделей*/
SELECT maker, COUNT(model) AS number_of_models
FROM pc JOIN product
	USING(model)
GROUP BY maker;

/*21 - Найдите максимальную цену ПК, выпускаемых каждым производителем.
Вывести: maker, максимальная цена.*/
SELECT maker, MAX(price) AS maximum_price
FROM pc JOIN product
	USING(model)
GROUP BY maker;

/*22 - Для каждого значения скорости ПК, превышающего 600 МГц, определите
среднюю цену ПК с такой же скоростью. Вывести: speed, средняя цена*/
SELECT speed, ROUND(AVG(price), 2) AS average_speed
FROM pc
WHERE speed > 600
GROUP BY speed
ORDER BY speed;

/*23 - Найдите производителей, которые производили бы как ПК со скоростью
не менее 750 МГц, так и ПК-блокноты со скоростью не менее 750 МГц.
Вывести: Maker*/
SELECT DISTINCT maker
FROM pc JOIN product
	USING(model)
WHERE speed >= 750 AND maker IN
	(
	SELECT DISTINCT maker
	FROM laptop JOIN product
		USING(model)
	WHERE speed >= 750
    );

/*24 - Перечислите номера моделей любых типов, имеющих самую высокую
цену по всей имеющейся в базе данных продукции*/
SELECT model
FROM
	(
		SELECT model, price FROM pc 
        WHERE price = (SELECT MAX(price) FROM pc)
		UNION
		SELECT model, price FROM laptop
        WHERE price = (SELECT MAX(price) FROM laptop)
        UNION 
        SELECT model, price FROM printer
        WHERE price = (SELECT MAX(price) FROM printer)
	) AS sel_1
WHERE sel_1.price = 
	(SELECT MAX(price)
    FROM (
			SELECT price FROM pc 
			WHERE price = (SELECT MAX(price) FROM pc)
			UNION
			SELECT price FROM laptop
			WHERE price = (SELECT MAX(price) FROM laptop)
			UNION 
			SELECT price FROM printer
			WHERE price = (SELECT MAX(price) FROM printer)
        ) sel_2
	) 
    
/*25 - Найдите производителей принтеров, которые производят ПК с
наименьшим объемом RAM и с самым быстрым процессором среди всех
ПК, имеющих наименьший объем RAM. Вывести: Maker*/
SELECT DISTINCT maker
FROM 
	(SELECT model, MAX(speed)
	FROM 
		(SELECT model, MIN(ram), speed
		FROM pc
		WHERE ram IN
			(SELECT MIN(ram) FROM pc)
		GROUP BY speed
		) AS sel_1
	) AS sel_2
JOIN product
	USING(model)
WHERE maker IN
	(SELECT DISTINCT maker
    FROM product
    WHERE type = 'printer'
    );
