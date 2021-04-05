INSERT product 
VALUES 
	('Epson', 'L132', 'Printer'),    
	('Epson', 'L805', 'Printer'),    
	('Canon', 'PIXMA G1411', 'Printer'),    
	('Canon', 'PIXMA G2415', 'Printer'),    
	('HP', 'LaserJet Pro M28w', 'Printer'),    
	('HP', 'Smart Tank 500 4SR29A', 'Printer'),    
	('HP', 'Laser 135r 5UE15A', 'Printer'),    
	('HP', 'LaserJet Pro MFP M426fdn', 'Printer'),    
	('Canon', 'i-SENSYS MF3010', 'Printer'),    
	('Canon', 'i-SENSYS LBP6030B', 'Printer'),    
	('TallyGenic', 'T2280+', 'Printer'),
	('TallyGenic', 'T2265+', 'Printer'),
	('Epson', 'LX-350', 'Printer'),
	('Epson', 'FX-2190', 'Printer'),
	('Epson', 'LX-1350', 'Printer'),
	('Batman', 'Forever', 'Printer'),
	('Intel', 'I-printer', 'Printer');

INSERT printer(model, color, type_technology, price)
VALUES 
	('L132', 'y', 'Jet', 184.45),
	('L805', 'y', 'Jet', 379.20),
	('PIXMA G1411', 'y', 'Jet', 138.45),
	('PIXMA G2415', 'y', 'Jet', 158.60),
	('LaserJet Pro M28w', 'n', 'Laser', 174.35),
	('Smart Tank 500 4SR29A', 'y', 'Jet', 164.75),
	('Laser 135r 5UE15A', 'n', 'Laser', 161.15),
	('LaserJet Pro MFP M426fdn', 'n', 'Laser', 671.75),
	('i-SENSYS MF3010', 'n', 'Laser', 305.10),
	('i-SENSYS LBP6030B', 'n', 'Laser', 138.45),
	('T2280+', 'n', 'Matrix', 2110.20),
	('T2265+', 'n', 'Matrix', 2110.20),
	('LX-350', 'n', 'Matrix', 190.00),
	('FX-2190', 'n', 'Matrix', 855.40),
	('LX-1350', 'n', 'Matrix', 445.50),
	('Forever', 'y', 'Matrix', 999.99),
	('I-printer', 'n', 'Matrix', 340.20);

INSERT product 
VALUES 
	('ASUS', 'X509JA-BQ012','Laptop'),
	('ASUS', 'UM431DA-AM057','Laptop'),
	('ASUS', 'FX706LI-H7009','Laptop'),
	('ASUS', 'F15 FX506LI-HN012','Laptop'),
	('HP', '17-by3043ur 22R43EA','Laptop'),
	('HP', '15-eh0035ur 2U3B2EA','Laptop'),
	('HP', '14s-fq0022ur 22M90EA','Laptop'),
	('DELL', 'Vostro 14 3491-294762','Laptop'),
	('DELL', 'XPS 15 7590-6401','Laptop'),
	('DELL', 'G3 15 3579-8808','Laptop'),
	('DELL', 'Inspiron 17 3793-212307','Laptop'),
	('Xiaomi', 'RedmiBook Air 13" JYU4315CN','Laptop'),
	('Xiaomi', 'Mi Notebook Pro 15.6" JYU4222CN','Laptop'),
	('Batman', 'X8Z','Laptop');
    
INSERT laptop (model, speed, ram, hd, price, screen)
VALUES
	('X509JA-BQ012', 1200, 8000, 256, 525.20, 15),
	('UM431DA-AM057', 2100, 8000, 1000, 875.00, 14),
	('FX706LI-H7009', 2500, 8000, 512, 1345.20, 17),
	('F15 FX506LI-HN012', 2500, 8000, 512, 1034.30, 15),
	('17-by3043ur 22R43EA', 1200, 8000, 256, 780.40, 17),
	('15-eh0035ur 2U3B2EA', 2300, 16000, 512, 874.10, 15),
	('14s-fq0022ur 22M90EA', 2600, 8000, 256, 635.95, 14),
	('Vostro 14 3491-294762', 1000, 8000, 256, 481.15, 14),
	('XPS 15 7590-6401', 2600, 16000, 512, 1950.45, 15),
	('G3 15 3579-8808', 2300, 8000, 1000, 925.45, 15),
	('Inspiron 17 3793-212307', 1200, 8000, 256, 1320.20, 17),
	('RedmiBook Air 13" JYU4315CN', 1000, 16000, 512, 1210.35, 14),
	('Mi Notebook Pro 15.6" JYU4222CN', 1800, 16000, 1024, 1670.75, 15),
	('X8Z', 8800, 32000, 8024, 4000, 15);
    
INSERT product 
VALUES
	('Intel','Core i5-10400F','PC'),
	('Intel','Core i5-11600KF','PC'),
	('Intel','Core i7-11700K','PC'),
	('Intel','Core i3-10100F','PC'),
	('Intel','Core i9-9900K','PC'),
	('Intel','Core i7-10700K','PC'),
	('AMD','Ryzen 5 3600','PC'),
	('AMD','Ryzen 9 5900X','PC'),
	('AMD','Ryzen 3 2200G','PC'),
	('Batman','Night Bat','PC');
    
INSERT pc (model, speed, ram, hd, cd, price)
VALUES
	('Core i5-10400F', 2900, 8000, 512, '12x', 680.00),
    ('Core i5-11600KF', 3900, 32000, 1024, '16x', 941.30),
    ('Core i7-11700K', 3600, 16000, 1024, '24x', 994.75),
    ('Core i3-10100F', 3600, 8000, 256, '8x', 345.10),
    ('Core i9-9900K', 3600, 32000, 512, '12x', 840.55),
    ('Core i7-10700K', 3900, 32000, 1024, '24x', 1080.20),
    ('Ryzen 5 3600', 3600, 16000, 512, '16x', 680.70),
    ('Ryzen 9 5900X', 3700, 32000, 1024, '32x', 1070.10),
    ('Ryzen 3 2200G', 3500, 8000, 512, '8x', 410.30),
    ('Night Bat', 4800, 32000, 2048, '32x', 4500);
    