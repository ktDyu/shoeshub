USE [SneakerStore]
GO

/****** Object:  Table [dbo].[chat_lieu]    Script Date: 26/12/2024 4:38:06 CH ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[chat_lieu](
	[macl] [int] IDENTITY(1,1) NOT NULL,
	[ten_chat_lieu] [nvarchar](255) NULL,
	[trang_thai] [int] NULL,
 CONSTRAINT [PK__chat_lie__7A21F84293A95C04] PRIMARY KEY CLUSTERED 
(
	[macl] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

USE [SneakerStore]
GO

/****** Object:  Table [dbo].[danh_muc]    Script Date: 26/12/2024 4:39:02 CH ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[danh_muc](
	[ma_dm] [int] IDENTITY(1,1) NOT NULL,
	[ten_danh_muc] [nvarchar](255) NULL,
	[trang_thai] [int] NULL,
 CONSTRAINT [PK__danh_muc__7A21E0208939EA57] PRIMARY KEY CLUSTERED 
(
	[ma_dm] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

USE [SneakerStore]
GO

/****** Object:  Table [dbo].[MAU_SAC]    Script Date: 26/12/2024 4:39:22 CH ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[MAU_SAC](
	[ma_ms] [int] IDENTITY(40,1) NOT NULL,
	[trang_thai] [bit] NULL,
	[ten_mau] [nvarchar](50) NULL,
 CONSTRAINT [PK__MAU_SAC__2725DFD6E6428A38] PRIMARY KEY CLUSTERED 
(
	[ma_ms] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

USE [SneakerStore]
GO

/****** Object:  Table [dbo].[SIZE]    Script Date: 26/12/2024 4:39:30 CH ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[SIZE](
	[ma_size] [int] IDENTITY(30,1) NOT NULL,
	[kich_thuoc] [int] NULL,
	[trang_thai] [bit] NULL,
 CONSTRAINT [PK__SIZE__A787E7EDDB530BC1] PRIMARY KEY CLUSTERED 
(
	[ma_size] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

USE [SneakerStore]
GO

/****** Object:  Table [dbo].[hinh_anh]    Script Date: 26/12/2024 4:39:42 CH ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[hinh_anh](
	[ma_anh] [int] IDENTITY(1,1) NOT NULL,
	[url1] [varchar](150) NULL,
	[trang_thai] [bit] NULL,
	[url2] [varchar](150) NULL,
	[url3] [varchar](150) NULL,
	[url4] [varchar](150) NULL,
 CONSTRAINT [PK_hinh_anh] PRIMARY KEY CLUSTERED 
(
	[ma_anh] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

USE [SneakerStore]
GO

/****** Object:  Table [dbo].[san_pham]    Script Date: 26/12/2024 4:40:16 CH ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[san_pham](
	[ma_sp] [int] IDENTITY(1,1) NOT NULL,
	[tensp] [nvarchar](255) NULL,
	[trang_thai] [int] NULL,
	[ma_dm] [int] NULL,
 CONSTRAINT [PK__san_pham__7A217672EDFB6BD9] PRIMARY KEY CLUSTERED 
(
	[ma_sp] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[san_pham]  WITH CHECK ADD  CONSTRAINT [FK5wavgec206es4cr9yeibf0jnx] FOREIGN KEY([ma_dm])
REFERENCES [dbo].[danh_muc] ([ma_dm])
GO

ALTER TABLE [dbo].[san_pham] CHECK CONSTRAINT [FK5wavgec206es4cr9yeibf0jnx]
GO

USE [SneakerStore]
GO

/****** Object:  Table [dbo].[chi_tiet_san_pham]    Script Date: 26/12/2024 4:40:26 CH ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[chi_tiet_san_pham](
	[mactsp] [int] IDENTITY(1,1) NOT NULL,
	[don_gia] [real] NULL,
	[ma_anh] [int] NULL,
	[mo_ta] [varchar](255) NULL,
	[so_luong] [int] NULL,
	[trang_thai] [int] NULL,
	[ma_cl] [int] NULL,
	[ma_ms] [int] NULL,
	[ma_sp] [int] NULL,
	[ma_size] [int] NULL,
 CONSTRAINT [PK__chi_tiet__50C457B4197A2AE7] PRIMARY KEY CLUSTERED 
(
	[mactsp] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[chi_tiet_san_pham]  WITH CHECK ADD  CONSTRAINT [FK_chi_tiet_san_pham_hinh_anh] FOREIGN KEY([ma_anh])
REFERENCES [dbo].[hinh_anh] ([ma_anh])
GO

ALTER TABLE [dbo].[chi_tiet_san_pham] CHECK CONSTRAINT [FK_chi_tiet_san_pham_hinh_anh]
GO

ALTER TABLE [dbo].[chi_tiet_san_pham]  WITH CHECK ADD  CONSTRAINT [FK9ykxtxsa89rcl8t25e0whtlnt] FOREIGN KEY([ma_size])
REFERENCES [dbo].[SIZE] ([ma_size])
GO

ALTER TABLE [dbo].[chi_tiet_san_pham] CHECK CONSTRAINT [FK9ykxtxsa89rcl8t25e0whtlnt]
GO

ALTER TABLE [dbo].[chi_tiet_san_pham]  WITH CHECK ADD  CONSTRAINT [FKd23x0fyd2kk6tm4mydffb5eju] FOREIGN KEY([ma_ms])
REFERENCES [dbo].[MAU_SAC] ([ma_ms])
GO

ALTER TABLE [dbo].[chi_tiet_san_pham] CHECK CONSTRAINT [FKd23x0fyd2kk6tm4mydffb5eju]
GO

ALTER TABLE [dbo].[chi_tiet_san_pham]  WITH CHECK ADD  CONSTRAINT [FKfnkm3eomblx1o557l7ia3vnaw] FOREIGN KEY([ma_sp])
REFERENCES [dbo].[san_pham] ([ma_sp])
GO

ALTER TABLE [dbo].[chi_tiet_san_pham] CHECK CONSTRAINT [FKfnkm3eomblx1o557l7ia3vnaw]
GO

ALTER TABLE [dbo].[chi_tiet_san_pham]  WITH CHECK ADD  CONSTRAINT [FKqymm8gk4qg7k9up43avfvd5tn] FOREIGN KEY([ma_cl])
REFERENCES [dbo].[chat_lieu] ([macl])
GO

ALTER TABLE [dbo].[chi_tiet_san_pham] CHECK CONSTRAINT [FKqymm8gk4qg7k9up43avfvd5tn]
GO

USE [SneakerStore]
GO

/****** Object:  Table [dbo].[khach_hang]    Script Date: 26/12/2024 4:42:39 CH ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[khach_hang](
	[makh] [int] IDENTITY(1,1) NOT NULL,
	[ten_kh] [nvarchar](50) NULL,
	[sdt] [nvarchar](40) NULL,
	[dia_chi] [nvarchar](50) NULL,
	[gioi_tinh] [bit] NULL,
	[ngay_sinh] [date] NULL,
	[trang_thai] [bit] NULL,
	[mat_khau] [varchar](100) NULL,
	[email] [nvarchar](50) NULL,
	[idkh] [nvarchar](40) NULL,
	[magh] [int] NULL,
 CONSTRAINT [PK_khach_hang] PRIMARY KEY CLUSTERED 
(
	[makh] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[khach_hang]  WITH CHECK ADD  CONSTRAINT [FK_khach_hang_gio_hang] FOREIGN KEY([makh])
REFERENCES [dbo].[gio_hang] ([magh])
GO

ALTER TABLE [dbo].[khach_hang] CHECK CONSTRAINT [FK_khach_hang_gio_hang]
GO

USE [SneakerStore]
GO

/****** Object:  Table [dbo].[dia_chi]    Script Date: 26/12/2024 4:43:00 CH ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[dia_chi](
	[madc] [int] IDENTITY(1,1) NOT NULL,
	[tendiachi] [nvarchar](50) NULL,
	[loaidiachi] [bit] NULL,
	[tinh_tp] [nvarchar](30) NULL,
	[quan_huyen] [nvarchar](30) NULL,
	[xa_phuong] [nvarchar](30) NULL,
	[mota] [nvarchar](30) NULL,
	[trang_thai] [bit] NULL,
	[makh] [int] NULL,
	[diachichitiet] [nvarchar](100) NULL,
	[tennguoinhan] [nvarchar](50) NULL,
	[sdtnguoinhan] [varchar](50) NULL,
 CONSTRAINT [PK_dia_chi] PRIMARY KEY CLUSTERED 
(
	[madc] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[dia_chi]  WITH CHECK ADD  CONSTRAINT [FK_dia_chi_khach_hang] FOREIGN KEY([makh])
REFERENCES [dbo].[khach_hang] ([makh])
GO

ALTER TABLE [dbo].[dia_chi] CHECK CONSTRAINT [FK_dia_chi_khach_hang]
GO

USE [SneakerStore]
GO

/****** Object:  Table [dbo].[hoa_don]    Script Date: 26/12/2024 4:43:20 CH ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[hoa_don](
	[mahd] [int] IDENTITY(1,1) NOT NULL,
	[tong_tien] [real] NULL,
	[trang_thai] [int] NULL,
	[ma_nv] [int] NULL,
	[ma_kh] [int] NULL,
	[tgtt] [datetime] NULL,
	[ghi_chu] [nvarchar](50) NULL,
	[tenhd] [nvarchar](50) NULL,
	[tong_sp] [int] NULL,
	[diachinguoinhan] [nvarchar](100) NULL,
	[sdtnguoinhan] [varchar](50) NULL,
	[tennguoinhan] [nvarchar](50) NULL,
	[tienship] [real] NULL,
	[hinhthucthanhtoan] [int] NULL,
 CONSTRAINT [PK_hoa_don1] PRIMARY KEY CLUSTERED 
(
	[mahd] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[hoa_don]  WITH CHECK ADD  CONSTRAINT [FK_hoa_don_khach_hang] FOREIGN KEY([ma_kh])
REFERENCES [dbo].[khach_hang] ([makh])
GO

ALTER TABLE [dbo].[hoa_don] CHECK CONSTRAINT [FK_hoa_don_khach_hang]
GO

USE [SneakerStore]
GO

/****** Object:  Table [dbo].[hoa_don_chi_tiet]    Script Date: 26/12/2024 4:43:30 CH ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[hoa_don_chi_tiet](
	[mahdct] [int] IDENTITY(1,1) NOT NULL,
	[mahd] [int] NULL,
	[mactsp] [int] NULL,
	[so_luong] [int] NULL,
	[don_gia] [real] NULL,
	[trang_thai] [int] NULL,
 CONSTRAINT [PK_hoa_don_chi_tiet] PRIMARY KEY CLUSTERED 
(
	[mahdct] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[hoa_don_chi_tiet]  WITH CHECK ADD  CONSTRAINT [FK_hoa_don_chi_tiet_chi_tiet_san_pham] FOREIGN KEY([mactsp])
REFERENCES [dbo].[chi_tiet_san_pham] ([mactsp])
GO

ALTER TABLE [dbo].[hoa_don_chi_tiet] CHECK CONSTRAINT [FK_hoa_don_chi_tiet_chi_tiet_san_pham]
GO

ALTER TABLE [dbo].[hoa_don_chi_tiet]  WITH CHECK ADD  CONSTRAINT [FK_hoa_don_chi_tiet_hoa_don] FOREIGN KEY([mahd])
REFERENCES [dbo].[hoa_don] ([mahd])
GO

ALTER TABLE [dbo].[hoa_don_chi_tiet] CHECK CONSTRAINT [FK_hoa_don_chi_tiet_hoa_don]
GO

USE [SneakerStore]
GO

/****** Object:  Table [dbo].[gio_hang]    Script Date: 26/12/2024 4:44:04 CH ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[gio_hang](
	[magh] [int] IDENTITY(1,1) NOT NULL,
	[makh] [int] NULL,
	[trang_thai] [bit] NULL,
 CONSTRAINT [PK_gio_hang] PRIMARY KEY CLUSTERED 
(
	[magh] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

USE [SneakerStore]
GO

/****** Object:  Table [dbo].[gio_hang_chi_tiet]    Script Date: 26/12/2024 4:44:21 CH ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[gio_hang_chi_tiet](
	[maghct] [int] IDENTITY(1,1) NOT NULL,
	[ma_gh] [int] NULL,
	[ma_ctsp] [int] NULL,
	[don_gia] [real] NULL,
	[so_luong] [int] NULL,
	[trang_thai] [bit] NULL,
 CONSTRAINT [PK_gio_hang_chi_tiet_1] PRIMARY KEY CLUSTERED 
(
	[maghct] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[gio_hang_chi_tiet]  WITH CHECK ADD  CONSTRAINT [FK_gio_hang_chi_tiet_chi_tiet_san_pham] FOREIGN KEY([ma_ctsp])
REFERENCES [dbo].[chi_tiet_san_pham] ([mactsp])
GO

ALTER TABLE [dbo].[gio_hang_chi_tiet] CHECK CONSTRAINT [FK_gio_hang_chi_tiet_chi_tiet_san_pham]
GO

ALTER TABLE [dbo].[gio_hang_chi_tiet]  WITH CHECK ADD  CONSTRAINT [FK_gio_hang_chi_tiet_gio_hang] FOREIGN KEY([ma_gh])
REFERENCES [dbo].[gio_hang] ([magh])
GO

ALTER TABLE [dbo].[gio_hang_chi_tiet] CHECK CONSTRAINT [FK_gio_hang_chi_tiet_gio_hang]
GO

USE [SneakerStore]
GO

/****** Object:  Table [dbo].[nhan_vien]    Script Date: 26/12/2024 4:44:39 CH ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[nhan_vien](
	[manv] [int] IDENTITY(1,1) NOT NULL,
	[ten_nv] [nvarchar](50) NULL,
	[sdt] [nvarchar](50) NULL,
	[dia_chi] [nvarchar](50) NULL,
	[ngay_sinh] [nvarchar](50) NULL,
	[mat_khau] [nvarchar](50) NULL,
	[trang_thai] [bit] NULL,
	[email] [nvarchar](50) NULL,
	[ma_cv] [int] NULL,
 CONSTRAINT [PK_nhan_vien] PRIMARY KEY CLUSTERED 
(
	[manv] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[nhan_vien]  WITH CHECK ADD  CONSTRAINT [FK_nhan_vien_chuc_vu] FOREIGN KEY([ma_cv])
REFERENCES [dbo].[chuc_vu] ([macv])
GO

ALTER TABLE [dbo].[nhan_vien] CHECK CONSTRAINT [FK_nhan_vien_chuc_vu]
GO

USE [SneakerStore]
GO

/****** Object:  Table [dbo].[chuc_vu]    Script Date: 26/12/2024 4:44:53 CH ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[chuc_vu](
	[macv] [int] IDENTITY(1,1) NOT NULL,
	[tencv] [nvarchar](50) NULL,
	[trang_thai] [bit] NULL,
 CONSTRAINT [PK_chuc_vu] PRIMARY KEY CLUSTERED 
(
	[macv] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

