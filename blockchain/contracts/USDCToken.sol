// SPDX-License-Identifier: MIT
// Compatible with OpenZeppelin Contracts ^5.0.0
pragma solidity ^0.8.20;

import "@openzeppelin/contracts/token/ERC20/ERC20.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

contract USDCToken is ERC20, Ownable {
    constructor()
        ERC20("USDCoin", "USDC")
        Ownable(msg.sender)
    {}

    /* allow anyone to mint for testing purposes */
    function mint(address to, uint256 amount) public {
        _mint(to, amount);
    }
}